package com.chernyshev.newsletterapp.presentation.splash

import com.chernyshev.newsletterapp.presentation.base.BaseCommand
import com.chernyshev.newsletterapp.presentation.base.BaseEvent
import com.chernyshev.newsletterapp.presentation.base.BaseViewState

data class ViewState(
    val command: Command? = null
) : BaseViewState

sealed class Event : BaseEvent {
    object FinishedFakeLoading : Event()
}

sealed class Command : BaseCommand {
    object NavigateToLanguageSelector : Command()
    object NavigateHome : Command()
}