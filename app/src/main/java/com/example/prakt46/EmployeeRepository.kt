package com.example.prakt46

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeRepository(private val employeeDao: EmployeeDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val employeeList: LiveData<List<Employee>> = employeeDao.getEmployees()

    fun insertEmployee(employee: Employee) {
        coroutineScope.launch(Dispatchers.IO) {
            employeeDao.insertEmployee(employee)
        }
    }

    fun deleteEmployee(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            employeeDao.deleteEmployee(id)
        }
    }
}