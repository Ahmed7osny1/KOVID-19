package com.sriyank.a3rdmedicalsummertrainingproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.sriyank.a3rdmedicalsummertrainingproject.service.VaccineReservationActivity
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
                    Toast.makeText(this, "You Logged Out", Toast.LENGTH_LONG).show()
                }
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
}