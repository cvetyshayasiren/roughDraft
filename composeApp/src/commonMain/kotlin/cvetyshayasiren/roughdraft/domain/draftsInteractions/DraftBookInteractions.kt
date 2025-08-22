package cvetyshayasiren.roughdraft.domain.draftsInteractions

import androidx.lifecycle.ViewModel
import cvetyshayasiren.roughdraft.data.DraftBookRepository
import cvetyshayasiren.roughdraft.domain.audioPlayer.AudioPlayerInteractions
import cvetyshayasiren.roughdraft.domain.settings.SettingsState
import cvetyshayasiren.roughdraft.domain.utils.custom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
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
        AudioPlayerInteractions.checkPlay()
    }

    fun nextPage() {
        val currentIndex = _draftBook.value.indexOf(_currentPage.value)
        val lastIndex = _draftBook.value.lastIndex
        setPage(if(currentIndex < lastIndex) _draftBook.value[currentIndex + 1] else _draftBook.value.first())
        AudioPlayerInteractions.checkPlay()
    }

    fun previousPage() {
        val currentIndex = _draftBook.value.indexOf(_currentPage.value)
        setPage(if(currentIndex > 0) _draftBook.value[currentIndex - 1] else _draftBook.value.last())
        AudioPlayerInteractions.checkPlay()
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