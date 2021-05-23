package com.bananadolphin.carrental.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bananadolphin.carrental.R
import com.bananadolphin.carrental.data.*
import com.bananadolphin.carrental.databinding.FragmentMakeRentalBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class MakeRentalFragment(var car: Car) : Fragment() {

    lateinit var fragmentMakeRentalBinding: FragmentMakeRentalBinding
    lateinit var rentalDurationET: EditText
    lateinit var rentalDatabase: RentalDatabase
    lateinit var rentalDao: RentalDao
    lateinit var currentUser: Client
    var specialClientPrice: Float = 0F
    var priceCoefficient: Float = 2F
    var rentalDurationText: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMakeRentalBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_make_rental, container, false)
        fragmentMakeRentalBinding.car = car

        rentalDatabase = RentalDatabase.getDatabase(requireContext())
        rentalDao = rentalDatabase.rentalDao()

        currentUser = rentalDao.getAllClients()[0]
        if (currentUser.clientExperience.toInt() <= 12) {
            priceCoefficient = 2F
        }else if(currentUser.clientExperience.toInt() in 13..35){
            priceCoefficient = 1.5F
        }else if(currentUser.clientExperience.toInt() in 35..60){
            priceCoefficient = 1.2F
        }else if(currentUser.clientExperience.toInt()  > 61){
            priceCoefficient = 1F
        }




        rentalDurationET = fragmentMakeRentalBinding.rentalDurationEditText



        rentalDurationET.addTextChangedListener {




        }
        rentalDurationET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.length != 0) {
                    rentalDurationText = rentalDurationET.text.toString().toInt()
                    specialClientPrice =
                        rentalDurationText * car.carPriceForMonth.toInt() * priceCoefficient!!
                    fragmentMakeRentalBinding.priceTV.text =
                        ("Personal price for you \n ${specialClientPrice}$")
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        fragmentMakeRentalBinding.makeOrder.setOnClickListener {
            if(specialClientPrice > 0){
                val c: Date = Calendar.getInstance().getTime()
                println("Current time => $c")

                val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
                val currentDate: String = df.format(c)
                rentalDao.insertOrder(
                    Order(
                        Random.nextLong(10000),
                        currentUser.clientId,
                        car.carId!!,
                        rentalDurationText,
                        specialClientPrice.toInt(),
                        currentDate
                    )
                )
                Toast.makeText(requireContext(), "Thank for your order", Toast.LENGTH_LONG).show()
                activity?.supportFragmentManager?.popBackStack()
            }
        }
        return fragmentMakeRentalBinding.root
    }


}