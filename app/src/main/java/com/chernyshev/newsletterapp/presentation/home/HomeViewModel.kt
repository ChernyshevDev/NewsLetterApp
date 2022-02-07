package com.chernyshev.newsletterapp.presentation.home

import androidx.lifecycle.viewModelScope
import com.chernyshev.newsletterapp.domain.usecase.NewsUseCase
import com.chernyshev.newsletterapp.presentation.base.BaseViewModel
import com.chernyshev.newsletterapp.presentation.utils.extensions.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCase: NewsUseCase
) : BaseViewModel<ViewState, Event, Command>(ViewState()) {
    override fun onReduceState(event: Event): ViewState {
        return when (event) {
            Event.ResumedFragment -> {
                launch { fetchNews() }
                state.copy(isLoading = true)
            }
            Event.PausedFragment -> {
                parentJob.cancelChildren()
                state
            }
            is Event.ReceivedNews -> {
                launch { startTimer() }
                state.copy(
                    isLoading = false,
                    news = event.news,
                    command = Command.UpdateNews(event.news)
                )
            }
            is Event.ClickedNewsItem -> state.copy(command = Command.ShowDescriptionDialog(event.newsItem))
            Event.Passed10Seconds, Event.ChangedLanguage -> {
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
        }
    }

    private suspend fun fetchNews() {
        newsUseCase.getTopNews()
            .onSuccess {
                sendEvent(Event.ReceivedNews(it))
            }
            .onError {
                sendEvent(Event.ReceivedError(it))
            }
    }

    private fun startTimer() {
        parentJob.cancelChildren()
        viewModelScope.launch(parentJob) {
            delay(10_000)
            sendEvent(Event.Passed10Seconds)
        }
    }

    override fun ViewState.clearCommand(): ViewState = this.copy(command = null)
    override val ViewState.command: Command?
        get() = this.command
}