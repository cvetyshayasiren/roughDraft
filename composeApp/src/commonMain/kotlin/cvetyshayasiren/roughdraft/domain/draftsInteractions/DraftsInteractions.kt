package cvetyshayasiren.roughdraft.domain.draftsInteractions

import cvetyshayasiren.roughdraft.data.DraftOptionsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object DraftsInteractions {
    private val _draftOptions: MutableStateFlow<List<DraftOptionsEntity>> = MutableStateFlow(listOf())
    val draftOptions: StateFlow<List<DraftOptionsEntity>> = _draftOptions.asStateFlow()

    private val _currentOption: MutableStateFlow<DraftOptionsEntity> = MutableStateFlow(DraftOptionsEntity())
    val currentOption: StateFlow<DraftOptionsEntity> = _currentOption.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            _draftOptions.value = DraftOptionsRepository().getSortedDrafts()
            _currentOption.value = _draftOptions.value.first()
        }
    }
}