package cvetyshayasiren.roughdraft.data

import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftPageEntity
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getStringArray
import roughdraft.composeapp.generated.resources.Res
import roughdraft.composeapp.generated.resources.drafts_names

class DraftBookRepository {

    suspend fun getSortedDraftBook(): List<DraftPageEntity> = getDraftBook().sortedByDescending { it.timestamp }

    suspend fun getDraftBook(): List<DraftPageEntity> = buildList {
        getDraftPageNames().forEach { draftName ->
            add(
                Json.decodeFromString< DraftPageDataEntity>(
                    string = Res.readBytes(
                        path = "files/${draftName}/options.json"
                    ).decodeToString()
                ).toDraftPageEntity(name = draftName)
            )
        }
    }

    suspend fun getDraftPageNames():  List<String> = getStringArray(Res.array.drafts_names)
}