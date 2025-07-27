package cvetyshayasiren.roughdraft

import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(
            position = WindowPosition(Alignment.Center)
        ),
        title = "roughDraft",
    ) {
        App()
    }
}