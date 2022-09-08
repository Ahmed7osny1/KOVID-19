package com.sriyank.a3rdmedicalsummertrainingproject.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequestArray

class doctorMessagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_messages)

        getData()

    }
    private fun getData() {

        //val list = arrayListOf<String>()

        Log.d("mytag", "Button clicked")

        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequestArray(
            this,
            Request.Method.GET,
            "/messages_of_patient",
            null,
            { response ->

                Log.d("mytag", "$response")

                /* for (i in 0 until  response.length()){

                     val test: JSONObject = response.getJSONObject(i)

                     // get the current student (json object) data
                     list.add(test.getString("vaccine_name"))

                 }
                 Log.d("mytag", "$list")*/

            },
            { error ->
                Log.e("mytag", "Error: $error - Status Code = ${error.networkResponse?.statusCode}")
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

        //return list
    }
}