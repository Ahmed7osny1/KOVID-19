package com.sriyank.a3rdmedicalsummertrainingproject.reservation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.UI.LoginActivity
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequest

class ReservedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserved)


        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequest(
            this,
            Request.Method.GET,
            "/patient-consultation",
            null,
            { response ->
                Log.d("mytag", "response = $response")

            },
            { error ->
                val statusCode = error.networkResponse?.statusCode
                Log.e("mytag", "Error: $error - Status Code = $statusCode")
                // if 401 unauthorized
                if (statusCode == 401) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                // if unknown error
                else {
                    Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
                }
            }
        )
        queue.add(request)

    }

}