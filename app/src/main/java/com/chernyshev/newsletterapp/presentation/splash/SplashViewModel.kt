package com.chernyshev.newsletterapp.presentation.splash

import com.chernyshev.newsletterapp.domain.repository.UserRepository
import com.chernyshev.newsletterapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<ViewState, Event, Command>(ViewState()) {
    override fun onReduceState(event: Event): ViewState {
        return when (event) {
            Event.FinishedFakeLoading -> state.copy(
                command = if (userRepository.isFirstTimeUser) {
                    Command.NavigateToLanguageSelector
                } else {
                    Command.NavigateHome
                }
            )
        }
    }

    override fun ViewState.clearCommand(): ViewState = this.copy(command = null)
    override val ViewState.command: Command?
        get() = this.command
}