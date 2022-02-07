package com.chernyshev.newsletterapp.presentation.language_selector

import com.chernyshev.newsletterapp.domain.entity.Language
import com.chernyshev.newsletterapp.presentation.base.BaseCommand
import com.chernyshev.newsletterapp.presentation.base.BaseEvent
import com.chernyshev.newsletterapp.presentation.base.BaseViewState

data class ViewState(
    val command: Command? = null
) : BaseViewState

sealed class Event : BaseEvent {
    data class SelectedLanguage(val language: Language) : Event()
}

sealed class Command : BaseCommand {
    object NavigateHome : Command()
}