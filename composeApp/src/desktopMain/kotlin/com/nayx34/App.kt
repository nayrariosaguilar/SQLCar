package com.nayx34

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import app.cash.sqldelight.db.SqlDriver
import com.nayx34.data.Database
import kotlinx.serialization.Serializable
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
        val controller = rememberNavController()
        NavHost(controller, startDestination = DogRoute){
            composable<HomeRoute>{ HomeScreen(controller) }
          composable<DogRoute>{ entry -> SelectCarById(database, 2, entry.toRoute(),controller) }
            composable<InsertDogWithReturning>{  entry -> InsertWithReturn(database, entry.toRoute(),controller) }
      }

    }
}
fun insertDatabase(db: Database){
    db.carQueries.insertCar("azul")
    db.carQueries.insertCar("verde")
}
@Composable
fun Nav(controller: NavController){
    TopAppBar(
        title = {Text("Barra de nav")},
        actions = {
            IconButton(onClick = {controller.navigate(DogRoute)}){
                Icon(imageVector = Icons.Filled.Home, contentDescription = "Home")
            }
        }
    )
}
@Serializable
object HomeRoute

@Serializable
object DogRoute
@Serializable
object InsertDogWithReturning

@Composable
fun HomeScreen(controller: NavController){
    Column{
        Nav(controller)
        Text("Metodo1")
        Button(onClick = {controller.navigate(DogRoute)}){
        }
        Text("Metodo2")
        Button(onClick = {controller.navigate(InsertDogWithReturning)}){
            Text("Country")
        }
    }
}
@Composable
fun CarListScreen(db: Database){
    val cars = db.carQueries.selectCar().executeAsList()
    LazyColumn { items(cars){ car -> Text(car.color)} }
}
@Composable
fun CarListScreenWithLimits(db: Database){
    val cars = db.carQueries.selectConLimits(3,0).executeAsList()
    LazyColumn { items(cars){ car -> Text(car.tuition.toString())} }
}
@Composable
fun SelectCarById(db: Database, dogId: Long, route: DogRoute,controller: NavController) {
    val car = db.carQueries.selectById(dogId).executeAsOne()
    Column {
        Nav(controller)
        Text(car.color)
        Text(car.tuition.toString())
    }

}
@Composable
fun InsertWithReturn(db: Database,route: DogRoute,controller: NavController) {
    val idefix = db.carQueries.insertWithReturn("Naysitacolor").executeAsOne()
       Text(idefix.toString())
}


