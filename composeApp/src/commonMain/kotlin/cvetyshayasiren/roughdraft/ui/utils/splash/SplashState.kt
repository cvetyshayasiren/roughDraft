package cvetyshayasiren.roughdraft.ui.utils.splash

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.viewModelScope
import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftBookInteractions
import cvetyshayasiren.roughdraft.ui.test.SplashTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Composable
fun rememberSplashState(isSplash: Boolean = true): SplashState {
    return rememberSaveable(saver = SplashState.Saver) { SplashState(isSplash = isSplash) }
}

class SplashState(isSplash: Boolean = true) {
    private val _isSplash: MutableStateFlow<Boolean> = MutableStateFlow(isSplash)
    val isSplash: StateFlow<Boolean> = _isSplash.asStateFlow()

    fun showSplash() = setIsSplash(true)

    fun hideSplash() = setIsSplash(false)

    fun setIsSplash(value: Boolean) {
        _isSplash.value = value
    }

    companion object {
        val Saver: Saver<SplashState, *> =
            Saver(save = { it._isSplash.value }, restore = { SplashState(it) })
    }
}

fun SplashState.delayThenHide(
    delay: Long = 1000,
    scope: CoroutineScope = DraftBookInteractions.viewModelScope
) {
    scope.launch {
        delay(delay)
        hideSplash()
    }
}