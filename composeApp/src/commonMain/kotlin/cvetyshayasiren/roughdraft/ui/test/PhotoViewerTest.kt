package cvetyshayasiren.roughdraft.ui.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cvetyshayasiren.roughdraft.ui.utils.photo.PhotoViewerDialog

@Composable
fun PhotoViewerTest() {
    val expandDialog = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PhotoViewerDialog(
            modifier = Modifier
                .size(400.dp)
                .clip(RoundedCornerShape(12.dp)),
            expandDialog = expandDialog,
            photoPaths = listOf("files/Любашинский сад/0.jpg"),
            initialPhotoIndex = 0,
        )
    }
}