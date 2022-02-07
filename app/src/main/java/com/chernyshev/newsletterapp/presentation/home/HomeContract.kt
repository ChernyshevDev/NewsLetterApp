package com.chernyshev.newsletterapp.presentation.home

import com.chernyshev.newsletterapp.domain.entity.Language
import com.chernyshev.newsletterapp.domain.entity.NewsItem
import com.chernyshev.newsletterapp.presentation.base.BaseCommand
import com.chernyshev.newsletterapp.presentation.base.BaseEvent
import com.chernyshev.newsletterapp.presentation.base.BaseViewState

data class ViewState(
    val command: Command? = null,
    val isLoading: Boolean = false,
    val news: List<NewsItem> = emptyList()
) : BaseViewState {
    val shouldShowShimmer by lazy {
        isLoading && news.isEmpty()
    }
}

sealed class Event : BaseEvent {
    object ResumedFragment : Event()
    object PausedFragment : Event()
    object Passed10Seconds : Event()
    data class ClickedNewsItem(val newsItem: NewsItem) : Event()
    object ChangedLanguage : Event()
    data class ReceivedNews(val news: List<NewsItem>) : Event()
    data class ReceivedError(val errorMessage: String?) : Event()
}

sealed class Command : BaseCommand {
    data class UpdateNews(val news: List<NewsItem>) : Command()
    data class ShowDescriptionDialog(val newsItem: NewsItem) : Command()
    data class DisplayError(val errorMessage: String?) : Command()
}