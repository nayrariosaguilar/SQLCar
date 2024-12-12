package com.nayx34

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.cash.sqldelight.db.SqlDriver
import com.nayx34.data.Database
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import nayrarios2uwu.composeapp.generated.resources.Res
import nayrarios2uwu.composeapp.generated.resources.compose_multiplatform
import javax.xml.crypto.Data

object DatabaseConfig {
    val name: String = "pets.db"
    val development: Boolean = true
}
@Composable
@Preview

fun App(sqlDriver: SqlDriver) {
    val database = Database(sqlDriver)
    Database.Schema.create(sqlDriver)

    if(DatabaseConfig.development){
        insertDatabase(database)
    }

    MaterialTheme {
        CarListScreen(database)
    }
}
fun insertDatabase(db: Database){
    db.carQueries.insertCar("azul")
    db.carQueries.insertCar("verde")

}

@Composable
fun CarListScreen(db: Database){
    val cars = db.carQueries.selectCar().executeAsList()
    LazyColumn { items(cars){ car -> Text(car.color)} }
}