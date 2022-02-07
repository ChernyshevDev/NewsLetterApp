package com.chernyshev.newsletterapp.presentation.language_selector

import com.chernyshev.newsletterapp.domain.repository.UserRepository
import com.chernyshev.newsletterapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageSelectorViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel<ViewState, Event, Command>(ViewState()) {
    override fun onReduceState(event: Event): ViewState {
        return when (event) {
            is Event.SelectedLanguage -> {
                userRepository.apply {
                    preferredNewsLanguage = event.language
                    isFirstTimeUser = false
                }
                state.copy(command = Command.NavigateHome)
            }
        }
    }

    override fun ViewState.clearCommand(): ViewState = this.copy(command = null)
    override val ViewState.command: Command?
        get() = this.command
}