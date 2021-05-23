package com.bananadolphin.carrental.data

import com.bananadolphin.carrental.data.Car

interface CarListener {
    fun carClick(car: Car)
    fun updateCar(car: Car)
    fun deleteCar(car: Car)
}