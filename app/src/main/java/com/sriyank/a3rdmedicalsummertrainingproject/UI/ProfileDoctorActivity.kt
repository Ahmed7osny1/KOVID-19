package com.sriyank.a3rdmedicalsummertrainingproject.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyConfig
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequest
import kotlinx.android.synthetic.main.activity_profile_doctor.*

class ProfileDoctorActivity : AppCompatActivity() {

    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close) }
    private val rotateTo: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom) }
    private val rotateFrom: Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom) }

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_doctor)

        logoutDoctor.visibility = View.INVISIBLE
        messageDoctor.visibility = View.INVISIBLE
        VideoCallDoctor.visibility = View.INVISIBLE

        add_fab.setOnClickListener {
            onAddButtonClick()
        }

        logoutDoctor.setOnClickListener{
            btnLogoutClicked()
            Toast.makeText(this,"logout",Toast.LENGTH_SHORT).show()
        }




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
                val profile = response.getJSONObject("doctor")

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

    private fun btnLogoutClicked(){

        Log.d("mytag", "Button logout clicked")
        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequest(
            this,
            Request.Method.GET,
            "/doctor_logout",
            null,
            { response ->
                Log.d("mytag", "response = $response")

                // remove token from shared prefs
                val prefs = getSharedPreferences(MyConfig.SHARED_PREFS_FILENAME, MODE_PRIVATE)
                val prefsEditor = prefs.edit()
                prefsEditor.remove("token")
                prefsEditor.apply()

                // go to login screen
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            },
            { error ->
                val statusCode = error.networkResponse?.statusCode
                Log.e("mytag", "Error: $error - Status Code = $statusCode")
                // if 401 unauthorized
                if(statusCode == 401){
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


    private fun onAddButtonClick() {
        setAnimation(clicked)
        setVisibility(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            logoutDoctor.visibility = View.VISIBLE
            messageDoctor.visibility = View.VISIBLE
            VideoCallDoctor.visibility = View.VISIBLE
        }else{
            logoutDoctor.visibility = View.INVISIBLE
            messageDoctor.visibility = View.INVISIBLE
            VideoCallDoctor.visibility = View.INVISIBLE
        }
    }

    private fun setAnimation(clicked: Boolean) {
        if(!clicked){
            logoutDoctor.startAnimation(rotateFrom)
            messageDoctor.startAnimation(rotateFrom)
            VideoCallDoctor.startAnimation(rotateFrom)
            add_fab.startAnimation(rotateOpen)
        }else{
            logoutDoctor.startAnimation(rotateTo)
            messageDoctor.startAnimation(rotateTo)
            VideoCallDoctor.startAnimation(rotateTo)
            add_fab.startAnimation(rotateClose)
        }
    }

    private fun setClickable(clicked: Boolean){

        if(!clicked) {
            logoutDoctor.isClickable = true
            messageDoctor.isClickable = true
            VideoCallDoctor.isClickable = true
        }else{
            logoutDoctor.isClickable = false
            messageDoctor.isClickable = false
            VideoCallDoctor.isClickable = false
        }

    }

}