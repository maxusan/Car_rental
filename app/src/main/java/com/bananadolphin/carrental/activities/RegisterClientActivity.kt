package com.bananadolphin.carrental.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bananadolphin.carrental.R
import com.bananadolphin.carrental.data.Client
import com.bananadolphin.carrental.data.RentalDao
import com.bananadolphin.carrental.data.RentalDatabase
import com.bananadolphin.carrental.databinding.ActivityRegisterClientBinding
import kotlin.random.Random

class RegisterClientActivity : AppCompatActivity() {

    lateinit var activityRegisterClientBinding: ActivityRegisterClientBinding
    lateinit var rentalDao: RentalDao
    lateinit var rentalDatabase: RentalDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRegisterClientBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_register_client)

        rentalDatabase = RentalDatabase.getDatabase(this)
        rentalDao = rentalDatabase.rentalDao()

        activityRegisterClientBinding.registerClientButton.setOnClickListener {
            if (activityRegisterClientBinding.clientNameEditText.text.toString() != "" &&
                activityRegisterClientBinding.clientSurnameEditText.text.toString() != "" &&
                activityRegisterClientBinding.clientExperienceEditText.text.toString() != ""
            ) {
                var client = Client(Random.nextLong(1000),
                    activityRegisterClientBinding.clientNameEditText.text.toString(),
                activityRegisterClientBinding.clientSurnameEditText.text.toString(),
                activityRegisterClientBinding.clientExperienceEditText.text.toString())
                rentalDao.insertClient(client)
                startActivity(Intent(this, CarsListActivity::class.java).putExtra("mode", 2))
                finish()
            } else {
                if (activityRegisterClientBinding.clientSurnameEditText.text.toString() == "") {
                    activityRegisterClientBinding.clientSurnameEditText.error =
                        getString(R.string.input_surname)
                }

                if (activityRegisterClientBinding.clientNameEditText.text.toString() == "") {
                    activityRegisterClientBinding.clientNameEditText.error =
                        getString(R.string.input_name)
                }

                if (activityRegisterClientBinding.clientExperienceEditText.text.toString() == "") {
                    activityRegisterClientBinding.clientExperienceEditText.error =
                        getString(R.string.input_experience)
                }
                Toast.makeText(this, "Please, input data about yourself", Toast.LENGTH_LONG).show()
            }
        }
    }
}