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

    /*private fun doctorMessageRecyclerView() {

        var list = ArrayList<MessageDate>()

        // send request
        val queue = Volley.newRequestQueue(this)

        val request = MyRequestArray(
            this,
            Request.Method.GET,
            "/messages_of_doctor",
            null,
            { response ->

                Log.d("mytag", "$response")

                if(response.length() > 0) {

                    noDataTextView.visibility = View.GONE

                    for (i in 0 until response.length()) {

                        val data = response.getJSONObject(i)

                        var messagereplay: String = "Not Replayed"
                        var Image = R.drawable.ic_notreplayed

                        if (response.getJSONObject(i).getString("reply") != "") {
                            messagereplay = response.getJSONObject(i).getString("reply")
                            Image = R.drawable.ic_replayed
                            replayNow.visibility = View.GONE
                            replayMessage.visibility = View.GONE
                        } else {
                            replay.visibility = View.GONE
                        }

                        list.add(
                            MessageDate(
                                Image,
                                data.getString("pat_fname"),
                                data.getString("pat_lname"),
                                data.getString("message"),
                                messagereplay,
                            )
                        )

                        messagesRecyclerView.layoutManager = LinearLayoutManager(
                            this,
                            RecyclerView.VERTICAL, false
                        )

                        val messageAdapter = MessageAdapter(list)

                        messagesRecyclerView.adapter = messageAdapter

                        messageAdapter.setonItemClickListener(object :
                            MessageAdapter.onItemClickListener {

                            override fun replayAction(position: Int) {
                                Log.d("mytag","$position")
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

    }*/

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
                        var messagereplay: String = "Not Replayed"
                        var Image = R.drawable.ic_notreplayed

                        if(response.getJSONObject(i).getString("reply") != ""){
                            messagereplay = response.getJSONObject(i).getString("reply")
                            Image = R.drawable.ic_replayed
                        }

                        list.add(
                            MessageDate(
                                Image,
                                data.getString("doctor_first_name"),
                                data.getString("doctor_last_name"),
                                data.getString("message"),
                                messagereplay,
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