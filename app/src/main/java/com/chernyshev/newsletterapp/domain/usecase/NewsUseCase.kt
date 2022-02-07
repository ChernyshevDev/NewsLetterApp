package com.chernyshev.newsletterapp.domain.usecase

import com.chernyshev.newsletterapp.domain.entity.NewsItem
import com.chernyshev.newsletterapp.presentation.base.OperationResult

interface NewsUseCase {
    suspend fun getTopNews(): OperationResult<List<NewsItem>>
}