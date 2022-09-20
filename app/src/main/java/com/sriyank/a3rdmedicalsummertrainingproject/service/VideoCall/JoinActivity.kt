package com.sriyank.a3rdmedicalsummertrainingproject.service.VideoCall

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.sriyank.a3rdmedicalsummertrainingproject.HomeActivity
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.UI.LoginActivity
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyConfig
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequest
import kotlinx.android.synthetic.main.activity_con_register.*
import kotlinx.android.synthetic.main.activity_join.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class JoinActivity : AppCompatActivity() {

    var age :Int = 0
    var sampleToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlrZXkiOiJmZjllY2I0Ny1mZDBjLTRhNTctYWIzZS0wMDE1NWRjMGZmYTQiLCJwZXJtaXNzaW9ucyI6WyJhbGxvd19qb2luIl0sImlhdCI6MTY2MzIyMDY2MywiZXhwIjoxNjYzODI1NDYzfQ.43fFlKVwvyUqrv2KUL8RxDi1LwvJ7t36AerVn26Jfbo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        dateConfirm.setOnClickListener {
            calenderShow()
        }

        val btnCreate = findViewById<Button>(R.id.btnCreateMeeting)
        //val btnJoin = findViewById<Button>(R.id.btnJoinMeeting)
        //val etMeetingId = findViewById<EditText>(R.id.etMeetingId)

        checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID)
        checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID)

        //Doctor Name
        doctorNameConfirm.text = intent.getStringExtra("doctorName")

        //Patient Name
        val prefs = getSharedPreferences(
            MyConfig.SHARED_PREFS_FILENAME,
            MODE_PRIVATE
        )
        val patientName = prefs.getString("PatientName", null)
        patientNameConfirm.text = patientName.toString()


        btnCreate.setOnClickListener {
            // we will explore this method in the next step
            createMeeting(sampleToken)
        }
        /*btnJoin.setOnClickListener { v: View? ->
            val intent = Intent(this@JoinActivity, MeetingActivity::class.java)
            intent.putExtra("token", sampleToken)
            //intent.putExtra("meetingId", etMeetingId.text.toString())
            startActivity(intent)
        }*/
    }

    private fun createMeeting(token: String) {
        AndroidNetworking.post("https://api.videosdk.live/v1/meetings")
            .addHeaders("Authorization", token)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    try {
                        var meetingId = response.getString("meetingId")
                        val intent = Intent(this@JoinActivity, MeetingActivity::class.java)
                        intent.putExtra("token", sampleToken)
                        intent.putExtra("meetingId", meetingId)
                        Log.d("mytag",meetingId)

                        confirmConsultation(meetingId)
                        //startActivity(intent)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onError(anError: ANError) {
                    anError.printStackTrace()
                    Toast.makeText(
                        this@JoinActivity, anError.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    companion object {
        private const val PERMISSION_REQ_ID = 22
        private val REQUESTED_PERMISSIONS = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
        )
    }

    private fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
        if (ContextCompat.checkSelfPermission(this, permission) !=
            PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode)
            return false
        }
        return true
    }

    private fun confirmConsultation(meetingId: String) {

        val prefs = getSharedPreferences(
            MyConfig.SHARED_PREFS_FILENAME,
            MODE_PRIVATE
        )
        val patientID = prefs.getString("PatientID", null)

        val params = JSONObject()

        params.put("con_title", consultationTitle.text.toString())
        params.put("con_date", dateConfirm.text.toString())
        params.put("con_meet_id", meetingId)
        params.put("doc_fname", intent.getStringExtra("doctorFName"))
        params.put("doc_lname", intent.getStringExtra("doctorLName"))
        params.put("con_desc", consultationDiscription.text.toString())
        params.put("patientId", patientID)


        Log.d("mytag", "Button clicked")

        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequest(
            this,
            Request.Method.POST,
            "/meeting-with-doctor",
            params,
            { response ->

                Log.d("mytag", "response = $response")

                if (response.getString("msg") == "successfully") {
                    Toast.makeText(this, "Reserved Successfully",
                        Toast.LENGTH_LONG).show()

                    Log.d("mytag","$meetingId")

                    startActivity(Intent(this,HomeActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this,"Reserved Failed",Toast.LENGTH_LONG).show()
                }

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

    private fun calenderShow() {

        val calendar= Calendar.getInstance()
        var today = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener
            { view, year, monthOfYear, dayOfMonth ->

                dateConfirm.setText("" + dayOfMonth + " - " + (monthOfYear+1) + " - " + year)

            }, year, month, day)

        //currentTimeMillis
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis())
        datePickerDialog.show()

    }

}