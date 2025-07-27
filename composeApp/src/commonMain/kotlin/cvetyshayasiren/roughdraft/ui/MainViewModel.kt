package cvetyshayasiren.roughdraft.ui

import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftOptionsEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getStringArray
import roughdraft.composeapp.generated.resources.Res
import roughdraft.composeapp.generated.resources.drafts_names

object MainViewModel {
    private val _draftOptions: MutableStateFlow<List<DraftOptionsEntity>> = MutableStateFlow(listOf())
    val draftOptions: StateFlow<List<DraftOptionsEntity>> = _draftOptions.asStateFlow()

    init {
        CoroutineScope(Dispatchers.Default).launch { init() }
    }

    private suspend fun init() {
        _draftOptions.value = buildList {
            getStringArray(Res.array.drafts_names).forEach {
                add(
                    Json.decodeFromString<DraftOptionsEntity>(
                        string = Res.readBytes(
                            path = "files/${it}/options.json"
                        ).decodeToString()
                    )
                )
            }
        }
    }
}