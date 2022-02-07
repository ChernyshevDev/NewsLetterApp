package com.chernyshev.newsletterapp.presentation.utils.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ViewModel.launch(block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch { block() }
}