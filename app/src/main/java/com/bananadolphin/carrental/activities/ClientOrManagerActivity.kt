package com.bananadolphin.carrental.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bananadolphin.carrental.R
import com.bananadolphin.carrental.data.RentalDao
import com.bananadolphin.carrental.data.RentalDatabase
import com.bananadolphin.carrental.databinding.ActivityClientOrManagerBinding

class ClientOrManagerActivity : AppCompatActivity() {

    lateinit var clientOrManagerBinding: ActivityClientOrManagerBinding
    lateinit var rentalDatabase: RentalDatabase
    lateinit var rentalDao: RentalDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clientOrManagerBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_client_or_manager
        )

        rentalDatabase = RentalDatabase.getDatabase(this)
        rentalDao = rentalDatabase.rentalDao()

        clientOrManagerBinding.imAManagerButton.setOnClickListener {
            startActivity(Intent(this, CarsListActivity::class.java).putExtra("mode", 1))
            finish()
        }
        clientOrManagerBinding.imAClientButton.setOnClickListener {
            if(rentalDao.getClientCount() != 0){
                startActivity(Intent(this, CarsListActivity::class.java).putExtra("mode", 2))
                finish()
            }else{
                startActivity(Intent(this, RegisterClientActivity::class.java))
                finish()
            }
        }
    }
}