package com.sriyank.a3rdmedicalsummertrainingproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.UI.LoginActivity
import com.sriyank.a3rdmedicalsummertrainingproject.UI.ProfileActivity
import com.sriyank.a3rdmedicalsummertrainingproject.UI.ReservedActivity
import com.sriyank.a3rdmedicalsummertrainingproject.UI.patientMessagesActivity
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyConfig
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequest
import com.sriyank.a3rdmedicalsummertrainingproject.service.Radiology.RadiologyActivity
import com.sriyank.a3rdmedicalsummertrainingproject.service.ReservePCRAnalysisActivity
import com.sriyank.a3rdmedicalsummertrainingproject.service.VaccineReservationActivity
import com.sriyank.a3rdmedicalsummertrainingproject.service.VideoCall.JoinActivity
import com.sriyank.a3rdmedicalsummertrainingproject.service.chattingDoctorActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.service_items.view.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Navigation Drawer
        navViewer()
        serviceSelect.VaccineReservation.setOnClickListener {
            startActivity(Intent(this, VaccineReservationActivity::class.java))
        }

        serviceSelect.ReservePCRAnalysis.setOnClickListener {
            startActivity(Intent(this, ReservePCRAnalysisActivity::class.java))
        }

        serviceSelect.chattingDoctor.setOnClickListener {
            startActivity(Intent(this, chattingDoctorActivity::class.java))
        }

        serviceSelect.Radiology.setOnClickListener {
            startActivity(Intent(this, RadiologyActivity::class.java))
        }

        serviceSelect.VideoCall.setOnClickListener {
            startActivity(Intent(this, JoinActivity::class.java))
        }

    }

    private fun navViewer() {

        nav_view.bringToFront()

        nav_view.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.home -> drawable_layout.closeDrawer(GravityCompat.START)
                R.id.About -> Toast.makeText(this, "About", Toast.LENGTH_LONG).show()
                R.id.logout -> {
                    // API
                    // btnLogoutClicked()
                    btnLogoutClicked()
                    Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show()
                }
                R.id.message -> startActivity(Intent(this, patientMessagesActivity::class.java))
                R.id.reserved -> startActivity(Intent(this, ReservedActivity::class.java))
                R.id.profile -> startActivity(Intent(this, ProfileActivity::class.java))
            }
            false
        }
    }

    /*
        [open Navigation Drawer] and check if Navigation is open
        or is close
     */
    fun openNavigationDrawer(view: View) {

        if (drawable_layout.isDrawerOpen(GravityCompat.START)) {
            drawable_layout.closeDrawer(GravityCompat.START)
        } else {
            drawable_layout.openDrawer(GravityCompat.START)
        }

    }

    private fun btnLogoutClicked(){

        Log.d("mytag", "Button logout clicked")
        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequest(
            this,
            Request.Method.GET,
            "/logout-patient",
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


}