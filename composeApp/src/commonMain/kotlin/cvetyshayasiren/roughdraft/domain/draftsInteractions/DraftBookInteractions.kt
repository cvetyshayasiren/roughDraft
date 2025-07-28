package cvetyshayasiren.roughdraft.domain.draftsInteractions

import cvetyshayasiren.roughdraft.data.DraftBookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object DraftBookInteractions {
    private val _draftBook: MutableStateFlow<List<DraftPageEntity>> = MutableStateFlow(listOf())
    val draftBook: StateFlow<List<DraftPageEntity>> = _draftBook.asStateFlow()

    private val _currentPage: MutableStateFlow<DraftPageEntity> = MutableStateFlow(DraftPageEntity())
    val currentPage: StateFlow<DraftPageEntity> = _currentPage.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            _draftBook.value = DraftBookRepository().getSortedDraftBook()
            _currentPage.value = _draftBook.value.first()
        }
    }

    fun setPage(name: String) {
        _draftBook.value.find { it.name == name }?.let { page ->
            _currentPage.value = page
        }
    }

    fun nextPage() {
        val currentIndex = _draftBook.value.indexOf(_currentPage.value)
        val lastIndex = _draftBook.value.lastIndex
        _currentPage.value =
            if(currentIndex < lastIndex) _draftBook.value[currentIndex + 1] else _draftBook.value.first()

    }

    fun previousPage() {
        val currentIndex = _draftBook.value.indexOf(_currentPage.value)
        _currentPage.value =
            if(currentIndex > 0) _draftBook.value[currentIndex - 1] else _draftBook.value.last()

    }
}