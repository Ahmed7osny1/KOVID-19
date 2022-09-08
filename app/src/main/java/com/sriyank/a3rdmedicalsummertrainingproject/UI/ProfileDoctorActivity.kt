package com.sriyank.a3rdmedicalsummertrainingproject.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequest
import kotlinx.android.synthetic.main.activity_profile_doctor.*

class ProfileDoctorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_doctor)

        backBtnProfile.setOnClickListener { onBackPressed() }

        appCompatButton.setOnClickListener { onBackPressed() }



        Log.d("mytag", "Button clicked")

        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequest(
            this,
            Request.Method.GET,
            "/doctor_data",
            null,
            { response ->

                Log.d("mytag", "response = $response")
                val profile = response.getJSONObject("patient")

                yourName.text = "${profile.getString("doc_fname")} ${profile.getString("doc_lname")}"
                yourPhone.text = profile.getLong("doc_phone").toString()
                DateBirth.text = profile.getString("doc_age")
                yourEmail.text = profile.getString("doc_email")

                // if there is an error (wrong email or password)
                if (response.has("error")) {
                    val errorMesssage = response.getString("error")
                    Toast.makeText(this, errorMesssage, Toast.LENGTH_SHORT).show()

                }
            },
            { error ->
                Log.e(
                    "mytag",
                    "Error: $error - Status Code = ${error.networkResponse?.statusCode}"
                )
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }
}