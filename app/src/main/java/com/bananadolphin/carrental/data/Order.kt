package com.bananadolphin.carrental.data

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true)
    var orderId: Long?,
    var clientId: Long,
    var carId: Long,
    var rentalDuration: Int,
    var rentalPrice: Int,
    var rentalDate: String
)
