package com.bananadolphin.carrental.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bananadolphin.carrental.*
import com.bananadolphin.carrental.adapters.CarsAdapter
import com.bananadolphin.carrental.data.*
import com.bananadolphin.carrental.databinding.ActivityCarsListBinding
import com.bananadolphin.carrental.fragments.ClientCabinetFragment
import com.bananadolphin.carrental.fragments.MakeRentalFragment
import java.lang.Exception
import kotlin.random.Random

class CarsListActivity : AppCompatActivity() {

    lateinit var activityCarsListBinding: ActivityCarsListBinding
    lateinit var carsRecyclerView: RecyclerView
    lateinit var carsAdapter: CarsAdapter
    lateinit var rentalDatabase: RentalDatabase
    lateinit var rentalDao: RentalDao
    lateinit var userList: MutableList<Client>
    var currentClient: Client? = null
    var carIntent = intent
    var mode: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCarsListBinding = DataBindingUtil.setContentView(this, R.layout.activity_cars_list)



        carsRecyclerView = activityCarsListBinding.carsRecyclerView

        rentalDatabase = RentalDatabase.getDatabase(this)
        rentalDao = rentalDatabase.rentalDao()
        userList = rentalDao.getAllClients()
        try{
            currentClient = userList[0]
        }catch (e: Exception){

        }

        activityCarsListBinding.client = currentClient
        setButtonMode()
        carsAdapter = CarsAdapter(object : CarListener {
            override fun carClick(car: Car) {
                if (mode == 2) {
                    supportFragmentManager.beginTransaction()
                        .add(R.id.parent, MakeRentalFragment(car)).addToBackStack(null).commit()

                }
            }

            override fun updateCar(car: Car) {

                val inflaterU = LayoutInflater.from(this@CarsListActivity)
                val dialogView: View = inflaterU.inflate(R.layout.update_car_dialog, null)

                val carManufacturerET = dialogView.findViewById<EditText>(R.id.carManufacturerET)
                carManufacturerET.setText(car.carManufacturer)
                val carModelET = dialogView.findViewById<EditText>(R.id.carModelET)
                carModelET.setText(car.carModel)
                val carYearET = dialogView.findViewById<EditText>(R.id.carYearET)
                carYearET.setText(car.carYear)
                val carPriceET = dialogView.findViewById<EditText>(R.id.carPriceET)
                carPriceET.setText(car.carPriceForMonth)
                val carColorET = dialogView.findViewById<EditText>(R.id.carColorET)
                carColorET.setText(car.carColor)
                AlertDialog.Builder(this@CarsListActivity)
                    .setTitle(getString(R.string.update_car))
                    .setView(dialogView)
                    .setIcon(R.drawable.ic_baseline_directions_car_24)
                    .setPositiveButton(
                        getString(R.string.update_car),
                        object : DialogInterface.OnClickListener {
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                val carManufacturer = carManufacturerET.text.toString()
                                val carModel = carModelET.text.toString()
                                val carYear = carYearET.text.toString()
                                val carPrice = carPriceET.text.toString()
                                val carColor = carColorET.text.toString()
                                car.carManufacturer = carManufacturer
                                car.carModel = carModel
                                car.carYear = carYear
                                car.carPriceForMonth = carPrice
                                car.carColor = carColor

                                rentalDao.updateCar(car)
                                carsAdapter.notifyDataSetChanged()
                            }

                        })
                    .show()
            }

            override fun deleteCar(car: Car) {
                AlertDialog.Builder(this@CarsListActivity)
                    .setTitle("Delete car?")
                    .setPositiveButton("Delete", object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            rentalDao.deleteCar(car)
                        }

                    })
                    .show()
            }

        }, mode)



        carsRecyclerView.adapter = carsAdapter

        rentalDao.getAllCars().observe(this, androidx.lifecycle.Observer {
            carsAdapter.submitList(it)
        })


    }

    private fun setButtonMode() {
        if (intent != null) {
            mode = intent.getIntExtra("mode", 1337)
            if (mode == 2) {
                activityCarsListBinding.addCarButton.visibility = View.GONE
                activityCarsListBinding.greetingsTextView.text = "Hello, ${currentClient?.clientName} ${currentClient?.clientSurname}"
                activityCarsListBinding.logOutButton.setOnClickListener {
                    rentalDao.deleteClient(currentClient!!)
                    startActivity(Intent(this, ClientOrManagerActivity::class.java))
                    finish()

                }
                activityCarsListBinding.clientCabinetButton.setOnClickListener {
                    supportFragmentManager.beginTransaction().add(R.id.parent, ClientCabinetFragment()).addToBackStack(null).commit()
                }
            } else {
                activityCarsListBinding.clientCabinetButton.visibility = View.GONE
                //activityCarsListBinding.greetingsTextView.visibility = View.GONE
                activityCarsListBinding.greetingsTextView.text = getString(R.string.welcome_back)
                activityCarsListBinding.logOutButton.setOnClickListener {
                    startActivity(Intent(this, ClientOrManagerActivity::class.java))
                    finish()
                }
            }
        }
        activityCarsListBinding.addCarButton.setOnClickListener {

            val inflater = LayoutInflater.from(this)
            val dialogView: View = inflater.inflate(R.layout.add_car_dialog, null)


            AlertDialog.Builder(this)
                .setTitle(getString(R.string.add_car))
                .setView(dialogView)
                .setIcon(R.drawable.ic_baseline_directions_car_24)
                .setPositiveButton(
                    getString(R.string.create_car),
                    object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            createCar(dialogView)
                        }

                    })
                .show()
        }
    }

    private fun createCar(dialogView: View) {
        val carManufacturerET = dialogView.findViewById<EditText>(R.id.carManufacturerET)
        val carModelET = dialogView.findViewById<EditText>(R.id.carModelET)
        val carYearET = dialogView.findViewById<EditText>(R.id.carYearET)
        val carPriceET = dialogView.findViewById<EditText>(R.id.carPriceET)
        val carColorET = dialogView.findViewById<EditText>(R.id.carColorET)

        val carManufacturer = carManufacturerET.text.toString()
        val carModel = carModelET.text.toString()
        val carYear = carYearET.text.toString()
        val carPrice = carPriceET.text.toString()
        val carColor = carColorET.text.toString()

        if (carManufacturer != "" &&
            carModel != "" &&
            carYear != "" &&
            carPrice != "" &&
            carColor != ""
        ) {
            val car =
                Car(Random.nextLong(1000), carManufacturer, carModel, carYear, carPrice, carColor)
            rentalDao.insertCar(car)

        } else {
            if (carModel == "") {
                carModelET.error = getString(R.string.unput_value)
            }
            if (carManufacturer == "") {
                carManufacturerET.error = getString(R.string.unput_value)
            }
            if (carPrice == "") {
                carPriceET.error = getString(R.string.unput_value)
            }
            if (carYear == "") {
                carYearET.error = getString(R.string.unput_value)
            }
            if (carColor == "") {
                carColorET.error = getString(R.string.unput_value)
            }
        }

    }
}