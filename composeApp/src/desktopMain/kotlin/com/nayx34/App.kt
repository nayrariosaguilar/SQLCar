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
        NavHost(controller, startDestination = HomeRoute){
            composable<HomeRoute>{ HomeScreen(controller) }
            composable<DogRoute>{ entry -> SelectCarById(database, 2, entry.toRoute(),controller) }
            composable<InsertDogWithReturning>{  entry -> InsertWithReturn(database, entry.toRoute(),controller) }
            composable<CarListScreenWithLimits>{  entry -> CarListScreenWithLimits(database, controller) }
            composable<CarListScreen>{  entry -> CarListScreen(database,controller, entry.toRoute()) }
            composable<deleteCarById>{  entry -> deleteCarById(database, 2,controller, entry.toRoute()) }
      }

    }
}
fun insertDatabase(db: Database){
    db.carQueries.insertCar("azul")
    db.carQueries.insertCar("rojo")
    db.carQueries.insertCar("negro")
    db.carQueries.insertCar("blanco")
    db.carQueries.insertCar("verde")
}
@Composable
fun Nav(controller: NavController){
    TopAppBar(
        title = {Text("Operaciones crud")},
        actions = {
            IconButton(onClick = {controller.navigate(HomeRoute)}){
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
@Serializable
object CarListScreenWithLimits
@Serializable
object CarListScreen
@Serializable
object deleteCarById


@Composable
fun HomeScreen(controller: NavController){
    Column{
        Nav(controller)
        Text("Metodo1")
        Button(onClick = {controller.navigate(DogRoute)}){
            Text("SelectCarById")
        }
        Text("Metodo2")
        Button(onClick = {controller.navigate(InsertDogWithReturning)}){
            Text("Insert with returning")
        }
        Text("Metodo3")
        Button(onClick = {controller.navigate(CarListScreen)}){
            Text("CarListScreen")
        }
        Text("Metodo4")
        Button(onClick = {controller.navigate(CarListScreenWithLimits)}){
            Text("CarListScreenWithLimits")
        }
        Text("Metodo5")
        Button(onClick = {controller.navigate(deleteCarById)}){
            Text("deleteCarById")
        }
    }
}
@Composable
fun CarListScreen(db: Database,controller: NavController,route: HomeRoute){
    val cars = db.carQueries.selectCar().executeAsList()
Column {
    Nav(controller)
    LazyColumn {

        items(cars){ car ->
            Text(car.color)
            Text(car.tuition.toString())
        } }

}
}
@Composable
fun CarListScreenWithLimits(db: Database,controller: NavController){
    val cars = db.carQueries.selectConLimits(3,0).executeAsList()
Column {
    Nav(controller)
    LazyColumn {
        items(cars){
                car ->
            Text(car.tuition.toString())
            Text(car.color)
        } }
}

}
@Composable
fun deleteCarById(db: Database,id:Long,controller: NavController,route: HomeRoute){
    val deletedCar=db.carQueries.deletefromId(id)
    Column {
        Nav(controller)
        if(deletedCar!= null){
            Text("delete ok")
        }else{
            Text("Error al intentar eliminarlo de la bbdd")
        }
    }
}
@Composable
fun SelectCarById(db: Database, dogId: Long, route: HomeRoute,controller: NavController) {
    val car = db.carQueries.selectById(dogId).executeAsOne()
    Column {
        Nav(controller)
        Text(car.color)
        Text(car.tuition.toString())
    }

}
@Composable
fun InsertWithReturn(db: Database,route: HomeRoute,controller: NavController) {
    Column {
        Nav(controller)
        val idefix = db.carQueries.insertWithReturn("Naysitacolor").executeAsOne()
        Text(idefix.toString())
    }
}


