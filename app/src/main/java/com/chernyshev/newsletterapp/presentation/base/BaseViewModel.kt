package com.chernyshev.newsletterapp.presentation.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<ViewState : BaseViewState, Event : BaseEvent, Command : BaseCommand>(
    initialState: ViewState
) : ViewModel() {
    private val _stateLiveData = MutableLiveData(initialState)
    private val stateLiveData = _stateLiveData as LiveData<ViewState>

    private val _commandLiveData = MutableLiveData<Command?>(null)
    private val commandLiveData = _commandLiveData as LiveData<Command?>

    val state get() = _stateLiveData.value ?: throw Exception("state cannot be null")

    fun sendEvent(event: Event) {
        val newState = onReduceState(event)
        if (newState == state) return
        val command = newState.command
        _stateLiveData.value = newState.clearCommand()
        _commandLiveData.value = command
    }

    fun subscribeToCommand(owner: LifecycleOwner, onCommand: (Command) -> Unit) {
        commandLiveData.observe(owner) {
            if (it != null) {
                onCommand(it)
                _commandLiveData.value = null
            }
        }
    }

    fun subscribeToStateUpdates(owner: LifecycleOwner, onUpdate: (ViewState) -> Unit) {
        stateLiveData.observe(owner) { onUpdate(it) }
    }

    protected abstract fun onReduceState(event: Event): ViewState
    protected abstract fun ViewState.clearCommand(): ViewState
    protected abstract val ViewState.command: Command?
}

interface BaseEvent
interface BaseCommand
interface BaseViewState