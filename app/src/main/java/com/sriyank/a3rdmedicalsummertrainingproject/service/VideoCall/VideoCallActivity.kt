package com.sriyank.a3rdmedicalsummertrainingproject.service.VideoCall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.DoctorAdapter
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.DoctorData
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequestArray
import kotlinx.android.synthetic.main.activity_video_call.*

class VideoCallActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_call)

        doctorRecyclerView()

    }

    private fun doctorRecyclerView() {

        var list = ArrayList<DoctorData>()

        Log.d("mytag", "Button clicked")

        // send request
        val queue = Volley.newRequestQueue(this)

        val request = MyRequestArray(
            this,
            Request.Method.GET,
            "/available-doctors",
            null,
            { response ->

                Log.d("mytag", "$response")

                if(response.length() > 0) {

                    for (i in 0 until response.length()) {

                        val data = response.getJSONObject(i)

                        var image: Int? = null
                        if(data.getString("doc_sex") == "male"){
                            image = R.drawable.doctormale
                        }else {
                            image = R.drawable.femaledoc
                        }
                        // get the current student (json object) data
                        list.add(
                            DoctorData(
                                image,
                                data.getString("doc_fname"),
                                data.getString("doc_lname"),
                                data.getString("doc_email"),
                                data.getString("doc_phone"),
                                data.getString("doc_age")
                            )
                        )

                        doctorRecyclerView.layoutManager = LinearLayoutManager(
                            this,
                            RecyclerView.VERTICAL, false
                        )

                        val doctorAdapter = DoctorAdapter(list)

                        doctorRecyclerView.adapter = doctorAdapter

                        doctorAdapter.setonItemClickListener(object: DoctorAdapter.onItemClickListener{

                            override fun bookAction(position: Int) {

                                val intent = Intent(this@VideoCallActivity,
                                    JoinActivity::class.java)
                                startActivity(intent)

                            }
                        })
                    }
                }
                Log.d("mytag", "$list")

            },
            { error ->
                Log.e("mytag", "Error: $error - Status Code = ${error.networkResponse?.statusCode}")
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(request)

    }

}