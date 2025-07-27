package cvetyshayasiren.roughdraft.data

import cvetyshayasiren.roughdraft.domain.draftsInteractions.DraftOptionsEntity
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getStringArray
import roughdraft.composeapp.generated.resources.Res
import roughdraft.composeapp.generated.resources.drafts_names

class DraftOptionsRepository {

    suspend fun getSortedDrafts(): List<DraftOptionsEntity> = getDrafts().sortedByDescending { it.timestamp }

    suspend fun getDrafts(): List<DraftOptionsEntity> = buildList {
        getDraftsNames().forEach { draftName ->
            add(
                Json.decodeFromString< DraftOptionsDataEntity>(
                    string = Res.readBytes(
                        path = "files/${draftName}/options.json"
                    ).decodeToString()
                ).toDraftOptionsEntity(name = draftName)
            )
        }
    }

    suspend fun getDraftsNames():  List<String> = getStringArray(Res.array.drafts_names)
}