package com.bananadolphin.carrental.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RentalDao {
    @Query("select * from orders")
    fun getAllOrdersLive(): LiveData<MutableList<Order>>

    @Query("select * from orders")
    fun getAllOrders(): MutableList<Order>

    @Query("select * from cars")
    fun getAllCars(): LiveData<MutableList<Car>>

    @Query("select * from clients")
    fun getAllClientsLive(): LiveData<MutableList<Client>>

    @Query("select * from clients")
    fun getAllClients():MutableList<Client>

    @Query("select * from cars where carId=:value")
    fun getCarById(value: Long):MutableList<Car>

    @Query("select count(*) from clients")
    fun getClientCount(): Int

    @Insert
    fun insertOrder(order: Order)

    @Update
    fun updateOrder(order: Order)

    @Delete
    fun deleteOrder(order: Order)

    @Insert
    fun insertClient(client: Client)

    @Update
    fun updateClient(client: Client)

    @Delete
    fun deleteClient(client: Client)

    @Insert
    fun insertCar(car: Car)

    @Update
    fun updateCar(car: Car)

    @Delete
    fun deleteCar(car: Car)

}