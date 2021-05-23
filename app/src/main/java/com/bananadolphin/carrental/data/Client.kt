package com.bananadolphin.carrental.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class Client(
    @PrimaryKey
    var clientId: Long,
    var clientName: String,
    var clientSurname: String,
    var clientExperience: String
)
