package com.sriyank.a3rdmedicalsummertrainingproject.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MessageAdapter
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MessageDate
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyConfig
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequestArray
import kotlinx.android.synthetic.main.activity_messages_reserved.*
import kotlinx.android.synthetic.main.messagedoctor.*

class messageResevedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messages_reserved)

            patientMessageRecyclerView()

    }

    private fun patientMessageRecyclerView() {


        var list = ArrayList<MessageDate>()

        // send request
        val queue = Volley.newRequestQueue(this)

        val request = MyRequestArray(
            this,
            Request.Method.GET,
            "/messages_of_patient",
            null,
            { response ->

                Log.d("mytag", "$response")

                if(response.length() > 0) {

                    noDataTextView.visibility = View.GONE

                    for (i in 0 until response.length()) {

                        val data = response.getJSONObject(i)

                        list.add(
                            MessageDate(
                                R.drawable.ic_replayed,
                                data.getString("doctor_first_name"),
                                data.getString("doctor_last_name"),
                                data.getString("message"),
                                data.getString("reply")
                            )
                        )

                        messagesRecyclerView.layoutManager = LinearLayoutManager(
                            this,
                            RecyclerView.VERTICAL, false
                        )

                        val messageAdapter = MessageAdapter(list)

                        messagesRecyclerView.adapter = messageAdapter

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