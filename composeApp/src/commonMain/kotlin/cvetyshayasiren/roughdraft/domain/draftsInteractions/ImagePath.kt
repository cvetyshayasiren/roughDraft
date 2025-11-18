package cvetyshayasiren.roughdraft.domain.draftsInteractions

import androidx.lifecycle.viewModelScope
import com.ashampoo.kim.Kim
import com.ashampoo.kim.common.convertToPhotoMetadata
import com.ashampoo.kim.model.PhotoMetadata
import com.github.panpf.sketch.fetch.newComposeResourceUri
import cvetyshayasiren.roughdraft.domain.map.RelativeCoordinates
import cvetyshayasiren.roughdraft.domain.map.toRelativeCoordinates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import roughdraft.composeapp.generated.resources.Res

typealias PhotoPath = String
typealias PhotoPaths = List<PhotoPath>

suspend fun PhotoPath.getPhotoMetaData(): PhotoMetadata? {
    Kim
        .readMetadata(Res.readBytes(this@getPhotoMetaData))
        ?.convertToPhotoMetadata()?.let { metadata ->
            return metadata
        }
    return null
}

fun PhotoPath.getUri(): String = Res.getUri(this)

fun PhotoPath.getComposeResourceUri(): String = newComposeResourceUri(getUri())

suspend fun PhotoPath.getStringPhotoMetaData(): String =
    buildString {
        getPhotoMetaData()?.let { photoMetaData ->
            photoMetaData.takenDate?.let {
                appendLine(
                    "time $it"
                )
            }
            photoMetaData.gpsCoordinates?.let { gps ->
                appendLine("gps ${gps.latLongString}")
            }
        }
    }

fun PhotoPath.getCoordinatesMetaData(
    scope: CoroutineScope = DraftBookInteractions.viewModelScope,
    callback: (coordinates: RelativeCoordinates) -> Unit
) {
    scope.launch {
        getPhotoMetaData()?.gpsCoordinates?.toRelativeCoordinates()?.let { relativeCoordinates ->
            callback(relativeCoordinates)
        }
    }
}

fun<T> List<T>.nextIndexLooped(currentIndex: Int): Int = if(currentIndex < lastIndex) currentIndex + 1 else 0
fun<T> List<T>.previousIndexLooped(currentIndex: Int): Int = if(currentIndex > 0) currentIndex - 1 else lastIndex