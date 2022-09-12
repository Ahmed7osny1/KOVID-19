package com.sriyank.a3rdmedicalsummertrainingproject.service.VideoCall

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sriyank.a3rdmedicalsummertrainingproject.R
import kotlinx.android.synthetic.main.activity_confirm_video_call.*

class confirmVideoCallActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_video_call)

        val doctorName = intent.getStringExtra("doctorName")

        doctorNameConfirm.text = doctorName

    }
}