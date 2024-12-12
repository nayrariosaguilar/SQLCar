package com.nayx34

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import javax.swing.DesktopManager
import kotlin.io.path.Path
import kotlin.io.path.deleteIfExists

fun main() = application {


    if (DatabaseConfig.development) {
        Path(DatabaseConfig.name).deleteIfExists()
    }
    val driver = JdbcSqliteDriver("jdbc:sqlite:${DatabaseConfig.name}")
    Window(
        onCloseRequest = ::exitApplication,
        title = "NayraRios2uwu",
    ) {
        App(driver)
    }
}