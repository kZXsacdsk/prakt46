package com.example.prakt46

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prakt46.ui.theme.Prakt46Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prakt46Theme {
                val owner = LocalViewModelStoreOwner.current

                owner?.let {
                    val viewModel: EmployeeViewModel = viewModel(
                        it,
                        "EmployeeViewModel",
                        EmployeeViewModelFactory(LocalContext.current.applicationContext as Application)
                    )
                    Program(viewModel)
                }
            }
        }
    }
}

@Composable
fun Program(viewModel: EmployeeViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "inputScreen") {
        composable("inputScreen") {
            InputScreen(viewModel, navController)
        }
        composable("employeeListScreen") {
            EmployeeListScreen(viewModel)
        }
    }
}

@Composable
fun InputScreen(viewModel: EmployeeViewModel, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = viewModel.firstname,
            onValueChange = { viewModel.changeFirstname(it) },
            label = { Text("Имя:") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        TextField(
            value = viewModel.lastname,
            onValueChange = { viewModel.changeLastname(it) },
            label = { Text("Фамилия:") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        TextField(
            value = viewModel.position,
            onValueChange = { viewModel.changePosition(it) },
            label = { Text("Должность:") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        Button(
            onClick = { viewModel.insertEmployee() },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Добавить", fontSize = 22.sp)
        }

        TextField(
            value = viewModel.id.toString(),
            onValueChange = { viewModel.changeId(it) },
            label = { Text("Id: ") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        Button(
            onClick = { viewModel.deleteEmployee(viewModel.id) },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Удалить", fontSize = 22.sp)
        }

        Button(
            onClick = { navController.navigate("employeeListScreen") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Показать базу данных", fontSize = 22.sp)
        }
    }
}

@Composable
fun EmployeeListScreen(viewModel: EmployeeViewModel) {
    val employeeList by viewModel.employeeList.observeAsState(listOf())

    LazyColumn(Modifier.fillMaxSize()) {
        item {
            EmployeeTitleRow()
        }
        items(employeeList) { employee ->
            EmployeeRow(employee)
        }
    }
}

@Composable
fun EmployeeRow(employee: Employee) {
    Row(
        Modifier
            .background(Color.Gray)
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text(employee.id.toString(), modifier = Modifier.weight(.2f), fontSize = 22.sp)
        Text("${employee.firstname} ${employee.lastname}", modifier = Modifier.weight(.6f), fontSize = 22.sp)
        Text(employee.position, modifier = Modifier.weight(.2f), fontSize = 22.sp)
    }
}

@Composable
fun EmployeeTitleRow() {
    Row(
        Modifier
            .background(Color.Gray)
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Text("Id", color = Color.White, modifier = Modifier.weight(.2f), fontSize = 22.sp)
        Text("ФИО", color = Color.White, modifier = Modifier.weight(.6f), fontSize = 22.sp)
        Text("Должность", color = Color.White, modifier = Modifier.weight(.2f), fontSize = 22.sp)
    }
}