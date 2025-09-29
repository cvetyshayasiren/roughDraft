package cvetyshayasiren.roughdraft.domain.splash

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class SplashStates {
    SALUTE, PREPARED;
    fun isPrepared() = this == PREPARED
    fun isSalute() = this == SALUTE
}

object SplashState: ViewModel() {
    private val _state: MutableStateFlow<SplashStates> = MutableStateFlow(SplashStates.SALUTE)
    val state: StateFlow<SplashStates> = _state.asStateFlow()

    fun toSalute() = setState(SplashStates.SALUTE)
    fun toPrepared() = setState(SplashStates.PREPARED)

    private fun setState(state: SplashStates) { _state.value = state }
}