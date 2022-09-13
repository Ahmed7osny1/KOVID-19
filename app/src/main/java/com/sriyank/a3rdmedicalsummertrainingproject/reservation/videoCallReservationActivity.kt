package com.sriyank.a3rdmedicalsummertrainingproject.reservation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.*
import kotlinx.android.synthetic.main.activity_video_call_reserved.*

class videoCallReservationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_call_reserved)

        val prefs = getSharedPreferences(
            MyConfig.SHARED_PREFS_FILENAME,
            MODE_PRIVATE
        )
        val type = prefs.getString("type", null)
        if(type == "patient") {
            patientVideoRecyclerView()
        }else{
            doctorVideoRecyclerView()
        }

    }

    private fun patientVideoRecyclerView() {

        var list = ArrayList<VideoData>()


        // send request
        val queue = Volley.newRequestQueue(this)

        val request = MyRequestArray(
            this,
            Request.Method.GET,
            "/patient-consultation",
            null,
            { response ->

                Log.d("mytag", "$response")

                if(response.length() > 0) {

                    noVideo.visibility = View.GONE

                    for (i in 0 until response.length()) {

                        val data = response.getJSONObject(i)

                        list.add(
                            VideoData(
                                data.getString("doc_fname"),
                                data.getString("doc_lname"),
                                data.getString("con_date"),
                                data.getString("con_title"),
                                data.getString("con_desc"),
                                data.getString("doc_email")
                            )
                        )

                        recVideoReserved.layoutManager = LinearLayoutManager(
                            this,
                            RecyclerView.VERTICAL, false
                        )

                        val videoAdapter = VideoAdapter(list)

                        recVideoReserved.adapter = videoAdapter

                        videoAdapter.setonItemClickListener(object: VideoAdapter.onItemClickListener{

                            override fun openAction(position: Int) {

                                Log.d("mytag",response.getJSONObject(position).getString("con_meet_id"))

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

    private fun doctorVideoRecyclerView() {

        var list = ArrayList<VideoData>()


        // send request
        val queue = Volley.newRequestQueue(this)

        val request = MyRequestArray(
            this,
            Request.Method.GET,
            "/doctor_consultation",
            null,
            { response ->

                Log.d("mytag", "$response")

                if(response.length() > 0) {

                    noVideo.visibility = View.GONE

                    for (i in 0 until response.length()) {

                        val data = response.getJSONObject(i)

                        list.add(
                            VideoData(
                                data.getString("pat_fname"),
                                data.getString("pat_lname"),
                                data.getString("con_date"),
                                data.getString("con_title"),
                                data.getString("con_desc"),
                                data.getString("pat_email")
                            )
                        )

                        recVideoReserved.layoutManager = LinearLayoutManager(
                            this,
                            RecyclerView.VERTICAL, false
                        )

                        val videoAdapter = VideoAdapter(list)

                        recVideoReserved.adapter = videoAdapter

                        videoAdapter.setonItemClickListener(object: VideoAdapter.onItemClickListener{

                            override fun openAction(position: Int) {

                                Log.d("mytag",response.getJSONObject(position).getString("con_meet_id"))

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