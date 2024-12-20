package com.example.prakt46

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employees")
    fun getEmployees(): LiveData<List<Employee>>

    @Insert
    fun insertEmployee(employee: Employee)

    @Query("DELETE FROM employees WHERE id = :id")
    fun deleteEmployee(id: Int)
}