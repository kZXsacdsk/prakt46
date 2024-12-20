package com.example.prakt46

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employees")
class Employee {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var firstname = ""
    var lastname = ""
    var position = ""

    constructor()

    constructor(firstname: String, lastname: String, position: String) {
        this.firstname = firstname
        this.lastname = lastname
        this.position = position
    }
}