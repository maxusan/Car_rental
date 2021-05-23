package com.bananadolphin.carrental.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cars")
data class Car(
    @PrimaryKey(autoGenerate = true)
    var carId: Long? = null,
    var carManufacturer: String,
    var carModel: String,
    var carYear: String,
    var carPriceForMonth: String,
    var carColor: String
)
