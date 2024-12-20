package com.example.prakt46

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class EmployeeViewModel(application: Application) : ViewModel() {
    val employeeList: LiveData<List<Employee>>
    private val repository: EmployeeRepository
    var id by mutableStateOf(0)
    var firstname by mutableStateOf("")
    var lastname by mutableStateOf("")
    var position by mutableStateOf("")

    init {
        val employeeDb = EmployeeRoomDatabase.getInstance(application)
        val employeeDao = employeeDb.employeeDao()
        repository = EmployeeRepository(employeeDao)
        employeeList = repository.employeeList
    }

    fun changeId(value: String) {
        id = value.toIntOrNull() ?: id
    }

    fun changeFirstname(value: String) {
        firstname = value
    }

    fun changeLastname(value: String) {
        lastname = value
    }

    fun changePosition(value: String) {
        position = value
    }

    // Метод для добавления нового сотрудника в базу данных
    fun insertEmployee() {
        // Создаем объект Employee и передаем его в репозиторий для вставки в базу данных
        repository.insertEmployee(Employee(firstname, lastname, position))
    }

    // Метод для удаления сотрудника по id
    fun deleteEmployee(id: Int) {
        // Удаляем сотрудника с конкретным id
        repository.deleteEmployee(id)
    }
}
