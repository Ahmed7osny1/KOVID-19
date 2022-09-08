package com.sriyank.a3rdmedicalsummertrainingproject.OnBoarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sriyank.a3rdmedicalsummertrainingproject.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()


    }
}