package cvetyshayasiren.roughdraft.domain.draftsInteractions

import androidx.lifecycle.viewModelScope
import com.ashampoo.kim.Kim
import com.ashampoo.kim.common.convertToPhotoMetadata
import com.ashampoo.kim.model.PhotoMetadata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import roughdraft.composeapp.generated.resources.Res

typealias ImagePath = String

fun ImagePath.getMetaData(
    scope: CoroutineScope = DraftBookInteractions.viewModelScope,
    callBack: (photoMetaData: PhotoMetadata) -> Unit
) {
    scope.launch {
        Kim
            .readMetadata(Res.readBytes(this@getMetaData))
            ?.convertToPhotoMetadata()?.let { metadata ->
                callBack(metadata)
            }
    }
}

fun ImagePath.getUri(): String = Res.getUri(this)