package com.bananadolphin.carrental.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Car::class, Client::class, Order::class], version = 1)
abstract class RentalDatabase: RoomDatabase() {
    abstract fun rentalDao(): RentalDao

    companion object {

        @Volatile
        private var INSTANCE: RentalDatabase? = null

        fun getDatabase(context: Context): RentalDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RentalDatabase::class.java,
                    "rental_database"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}