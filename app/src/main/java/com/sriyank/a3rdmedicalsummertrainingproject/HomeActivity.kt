package com.sriyank.a3rdmedicalsummertrainingproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Navigation Drawer
        navViewer()



    }


    private fun navViewer(){

        nav_view.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.home -> drawable_layout.closeDrawer(GravityCompat.START)
                R.id.settings -> Toast.makeText(this,"settings",Toast.LENGTH_LONG).show()
                R.id.logout -> {
                    // API
                    // btnLogoutClicked()
                    Toast.makeText(this,"You Logged Out",Toast.LENGTH_LONG).show()
                }
                R.id.profile -> startActivity(Intent(this,ProfileFragment::class.java))
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