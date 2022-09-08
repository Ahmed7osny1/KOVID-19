package com.sriyank.a3rdmedicalsummertrainingproject.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.sriyank.a3rdmedicalsummertrainingproject.R
import kotlinx.android.synthetic.main.activity_forget_successful_send.*

class ForgetSuccessfulSendActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_successful_send)

        repasslottie.visibility = View.VISIBLE

        Handler().postDelayed({
            startActivity(Intent(this,LoginActivity::class.java))
        },3500)

    }
}