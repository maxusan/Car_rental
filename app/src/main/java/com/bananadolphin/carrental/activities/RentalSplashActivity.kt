package com.bananadolphin.carrental.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.bananadolphin.carrental.R

class RentalSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rental_splash)

        splash()
    }

    private fun splash() {
        object :CountDownTimer(3000, 10){
            override fun onTick(p0: Long) {
            }

            override fun onFinish() {
                startActivity(Intent(this@RentalSplashActivity, ClientOrManagerActivity::class.java))
                finish()
            }

        }.start()
    }
}