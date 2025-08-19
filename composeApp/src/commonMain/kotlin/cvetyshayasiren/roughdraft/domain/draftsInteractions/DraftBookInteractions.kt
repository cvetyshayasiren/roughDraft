package cvetyshayasiren.roughdraft.domain.draftsInteractions

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import cvetyshayasiren.roughdraft.data.DraftBookRepository
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayerInteractions
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.domain.utils.custom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

object DraftBookInteractions: ViewModel() {
    private val _draftBook: MutableStateFlow<List<DraftPageEntity>> = MutableStateFlow(listOf())
    val draftBook: StateFlow<List<DraftPageEntity>> = _draftBook.asStateFlow()

    private val _currentPage: MutableStateFlow<DraftPageEntity> = MutableStateFlow(DraftPageEntity())
    val currentPage: StateFlow<DraftPageEntity> = _currentPage.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            _draftBook.value = DraftBookRepository().getSortedDraftBook()
            setPage(_draftBook.value.first())
        }
    }

    fun setPage(name: String) {
        _draftBook.value.find { it.name == name }?.let { page ->
            setPage(page)
        }
        AudioPlayerInteractions.swapPlay()
    }

    fun nextPage() {
        val currentIndex = _draftBook.value.indexOf(_currentPage.value)
        val lastIndex = _draftBook.value.lastIndex
        setPage(if(currentIndex < lastIndex) _draftBook.value[currentIndex + 1] else _draftBook.value.first())
        AudioPlayerInteractions.swapPlay()
    }

    fun previousPage() {
        val currentIndex = _draftBook.value.indexOf(_currentPage.value)
        setPage(if(currentIndex > 0) _draftBook.value[currentIndex - 1] else _draftBook.value.last())
        AudioPlayerInteractions.swapPlay()
    }

    private fun setPage(page: DraftPageEntity) {
        _currentPage.value = page
        SettingsState.setSettings(themeSeedColor = page.color)
    }
}

fun DraftBookInteractions.isPageInit(
    scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    started: SharingStarted = SharingStarted.custom()
): StateFlow<Boolean> =
    draftBook.map {
        it.isNotEmpty()
    }.stateIn(
        scope = scope,
        started = started,
        initialValue = false
    )