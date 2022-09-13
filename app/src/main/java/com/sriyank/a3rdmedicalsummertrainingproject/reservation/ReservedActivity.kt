package com.sriyank.a3rdmedicalsummertrainingproject.reservation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sriyank.a3rdmedicalsummertrainingproject.R
import kotlinx.android.synthetic.main.activity_reserved.*

class ReservedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserved)

        viewDose.setOnClickListener { startActivity(Intent(this,
            doseReservedActivity::class.java)) }

        viewVideoCall.setOnClickListener { startActivity(Intent(this,
            videoCallReservationActivity::class.java)) }

        viewTest.setOnClickListener {
            startActivity(Intent(this,
                testReservedActivity::class.java)
        ) }

    }

}