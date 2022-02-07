package com.chernyshev.newsletterapp.presentation.home

import androidx.lifecycle.viewModelScope
import com.chernyshev.newsletterapp.domain.usecase.NewsUseCase
import com.chernyshev.newsletterapp.presentation.base.BaseViewModel
import com.chernyshev.newsletterapp.presentation.base.OperationResult
import com.chernyshev.newsletterapp.presentation.utils.extensions.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : BaseViewModel<ViewState, Event, Command>(ViewState()) {
    private val job = Job()

    override fun onReduceState(event: Event): ViewState {
        return when (event) {
            Event.PausedFragment -> {
                job.cancelChildren()
                state
            }
            Event.ResumedFragment -> {
                launch { fetchNews() }
                state.copy(isLoading = true)
            }
            is Event.ClickedNewsItem -> state.copy(command = Command.ShowDescriptionDialog(event.newsItem))
            is Event.ReceivedNews -> {
                launch { startTimer() }
                state.copy(
                    isLoading = false,
                    news = event.news,
                    command = Command.UpdateNews(event.news)
                )
            }
            Event.Passed10Seconds -> {
                launch { fetchNews() }
                state
            }
            is Event.ReceivedError -> {
                launch { fetchNews() }
                state.copy(
                    isLoading = false,
                    command = Command.DisplayError(event.errorMessage)
                )
            }
            is Event.ChangedLanguage -> {
                job.cancelChildren()
                launch { fetchNews() }
                state
            }
        }
    }

    private suspend fun fetchNews() {
        when (val result = newsUseCase.getTopNews()) {
            is OperationResult.ResultSuccess -> sendEvent(Event.ReceivedNews(result.result))
            is OperationResult.ResultError -> sendEvent(Event.ReceivedError(result.error))
        }
    }

    private fun startTimer() {
        job.cancelChildren()
        viewModelScope.launch(job) {
            delay(10_000)
            sendEvent(Event.Passed10Seconds)
        }
    }

    override fun ViewState.clearCommand(): ViewState = this.copy(command = null)
    override val ViewState.command: Command?
        get() = this.command
}