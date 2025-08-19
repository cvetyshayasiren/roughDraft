package cvetyshayasiren.roughdraft.domain.splash

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class SplashStates {
    LOADING, SALUTE, PREPARED
}

object SplashState: ViewModel() {
    private val _state: MutableStateFlow<SplashStates> = MutableStateFlow(SplashStates.SALUTE)
    val state: StateFlow<SplashStates> = _state.asStateFlow()

    fun nextState() {
        when(_state.value) {
            SplashStates.LOADING -> setState(SplashStates.SALUTE)
            SplashStates.SALUTE -> setState(SplashStates.PREPARED)
            SplashStates.PREPARED -> { }
        }
    }

    fun toSplash() = setState(SplashStates.SALUTE)
    fun toPrepared() = setState(SplashStates.PREPARED)

    private fun setState(state: SplashStates) { _state.value = state }
}