package com.nayx34

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "NayraRios2uwu",
    ) {
        App()
    }
}