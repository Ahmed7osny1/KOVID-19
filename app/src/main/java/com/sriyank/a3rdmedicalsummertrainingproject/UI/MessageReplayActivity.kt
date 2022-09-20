package com.sriyank.a3rdmedicalsummertrainingproject.UI

import android.content.Intent
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
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_message_replay.*
import kotlinx.android.synthetic.main.messagedoctor.*
import org.json.JSONObject

class MessageReplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_replay)

        doctorMessageRecyclerView()
    }

    private fun doctorMessageRecyclerView() {

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

                        list.add(
                            MessageDate(
                                R.drawable.ic_replayed,
                                data.getString("pat_fname"),
                                data.getString("pat_lname"),
                                data.getString("message"),
                                data.getString("reply")
                            )
                        )

                        messagesRecyclerView.layoutManager = LinearLayoutManager(
                            this,
                            RecyclerView.VERTICAL, false
                        )

                        val messageAdapter = MessageAdapterDoctor(list)

                        messagesRecyclerView.adapter = messageAdapter


                        messageAdapter.setonItemClickListener(object : MessageAdapterDoctor.onItemClickListener {

                            override fun replyAction(position: Int,reply: String) {
                                replyMessage(
                                    response.getJSONObject(position).getString("msg_id"),
                                    intent.getStringExtra("doc_id"),
                                    reply
                                )
                                Log.d("mytag", "$reply ")
                                Log.d("mytag", "${response.getJSONObject(position).getString("msg_id")}")
                                Log.d("mytag", "${intent.getStringExtra("doc_id")}")
                            }
                        })
                    }
                }


            },
            { error ->
                Log.e("mytag", "Error: $error - Status Code = ${error.networkResponse?.statusCode}")
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(request)

    }

    private fun replyMessage(msg: String,doc: String?,reply: String) {

        val params = JSONObject()
        params.put("reply", reply)
        params.put("msg_id", msg)
        params.put("doc_id", doc)

        // send request
        val queue = Volley.newRequestQueue(this)

        val request = MyRequest(
            this,
            Request.Method.POST,
            "/reply_to_message",
            params,
            { response ->

                Log.d("mytag", "$response")

                if(response.getString("msg") == "successfully"){
                    Toast.makeText(this,"Replayed successfully",
                    Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,MessageReplayActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this,"Replayed failed",
                        Toast.LENGTH_LONG).show()
                }

            },
            { error ->
                Log.e("mytag", "Error: $error - Status Code = ${error.networkResponse?.statusCode}")
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
            }
        )

        queue.add(request)

    }

}