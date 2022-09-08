package com.sriyank.a3rdmedicalsummertrainingproject.service

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.HomeActivity
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequest
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequestArray
import kotlinx.android.synthetic.main.activity_chatting_doctor.*
import org.json.JSONObject


class chattingDoctorActivity : AppCompatActivity() {

    private lateinit var doctorSelected: String
    private lateinit var EmailSend: String
    private var EmailSeslected = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting_doctor)

        backBtn.setOnClickListener { onBackPressed() }

        val items1 = getData()

        val arrayAdapterDoctor = ArrayAdapter(this, R.layout.dropdown_item, items1)

        menuDoctor.setAdapter(arrayAdapterDoctor)

        menuDoctor.onItemClickListener =
            OnItemClickListener { adapterView, view, position, id ->
                doctorSelected = arrayAdapterDoctor.getItem(position).toString()
                EmailSend = EmailSeslected[position]
                //Toast.makeText(this,"$doctorSelected : $EmailSend",Toast.LENGTH_LONG).show()
            }

        sendDoctor.setOnClickListener {

            if(mEditMassage.text!!.isNotEmpty() && messageContent.text!!.isNotEmpty()
                && doctorSelected.isNotEmpty()) {

                sendMessage(EmailSend, messageContent.text.toString())

                Toast.makeText(this, "SEND SUCCESSFUL", Toast.LENGTH_LONG).show()
            }else{

                if(doctorSelected.isEmpty())
                    Toast.makeText(this, "Choose your Doctor", Toast.LENGTH_LONG).show()

                if(mEditMassage.text!!.isEmpty())
                    Toast.makeText(this, "Enter your Title Message", Toast.LENGTH_LONG).show()

                if(messageContent.text!!.isEmpty())
                    Toast.makeText(this, "Enter your Content Message", Toast.LENGTH_LONG).show()

            }
        }


    }

    private fun getData(): ArrayList<String> {

        val arrayList = arrayListOf<String>()

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

                for (i in 0 until response.length()) {

                    val doctor: JSONObject = response.getJSONObject(i)


                    // get the current student (json object) data
                    var fname = doctor.getString("doc_fname")
                    var lname = doctor.getString("doc_lname")

                    var email = doctor.getString("doc_email")

                    EmailSeslected.add(email.toString())

                    arrayList.add("$fname $lname")
                }
                Log.d("mytag", "$arrayList , $EmailSeslected")

            },
            { error ->
                Log.e("mytag", "Error: $error - Status Code = ${error.networkResponse?.statusCode}")
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
        return arrayList
    }

    private fun sendMessage(res: String, message: String) {

        val params = JSONObject()
        params.put("doctor_email", res)
        params.put("msg", message)

        Log.d("mytag", "Button clicked")

        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequest(
            this,
            Request.Method.POST,
            "/contact-with-doctor",
            params,
            { response ->

                Log.d("mytag", "response = $response")

                // goto profile activity
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()

                // if there is an error (wrong email or password)
                if (response.has("error")) {
                    val errorMesssage = response.getString("error")
                    Toast.makeText(this, errorMesssage, Toast.LENGTH_SHORT).show()

                }
            },
            { error ->
                Log.e(
                    "mytag",
                    "Error: $error - Status Code = ${error.networkResponse?.statusCode}"
                )
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }

}