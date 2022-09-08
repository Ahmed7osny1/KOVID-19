package com.sriyank.a3rdmedicalsummertrainingproject.service

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.HomeActivity
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequest
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequestArray
import kotlinx.android.synthetic.main.activity_reserve_pcranalysis.*
import org.json.JSONObject
import java.util.*
import java.util.Date

class ReservePCRAnalysisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_pcranalysis)

        backBtnVaccie.setOnClickListener { onBackPressed() }

        val items1 = getData()

        val arrayAdapterTest = ArrayAdapter(this, R.layout.dropdown_item, items1)

        menuTests.setAdapter(arrayAdapterTest)

        val TestSelected: String = menuTests.text.toString()

        // choose healthCare center
        val items2 = getDataCare()

        val arrayAdapterCenter = ArrayAdapter(this, R.layout.dropdown_item, items2)

        menuCenter.setAdapter(arrayAdapterCenter)

        val LocationSelected: String = menuCenter.text.toString()

        time.setOnClickListener {

            val timePicker: TimePickerDialog = TimePickerDialog(
                this,
                timePicker(),
                12,
                10,
                false
            )

            timePicker.show()

        }

        Date.setOnClickListener { calenderShow() }

        confirm.setOnClickListener {

            if (time.text!!.isNotEmpty() && Date.text!!.isNotEmpty()) {
                ReserveApi(
                    TestSelected,
                    Date.text.toString(),
                    time.text.toString(),
                    LocationSelected
                )
            }
            else {


                if (Date.text!!.isEmpty())
                    Toast.makeText(this, "Please select Date", Toast.LENGTH_LONG).show()

                if (time.text!!.isEmpty())
                    Toast.makeText(this, "Please select Time", Toast.LENGTH_LONG).show()

            }
        }

    }

    private fun ReserveApi(type: String,Date: String,time: String,location: String) {

        val params = JSONObject()

        params.put("dose_name", type)
        params.put("dose_date", Date)
        params.put("dose_time", time)
        params.put("dose_patient_health_name", location)


        Log.d("mytag", "Button clicked")

        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequest(
            this,
            Request.Method.POST,
            "/dose-reservation",
            params,
            { response ->

                Log.d("mytag", "response = $response")
                Toast.makeText(this@ReservePCRAnalysisActivity,
                    "$response", Toast.LENGTH_LONG).show()
                // goto Home activity
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()

                // if there is an error (wrong email or password)
                if (response.has("error")) {
                    val errorMesssage = response.getString("error")
                    Toast.makeText(this@ReservePCRAnalysisActivity, errorMesssage,
                        Toast.LENGTH_SHORT)
                        .show()

                }
            },
            { error ->
                Log.e("mytag",
                    "Error: $error - Status Code = ${error.networkResponse?.statusCode}")
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

    }


    private fun calenderShow(){

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener
            { view, year, monthOfYear, dayOfMonth ->
                Date.setText("$year-${(monthOfYear + 1)}-$dayOfMonth")
            }, year, month, day
        )
        //currentTimeMillis
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis())
        datePickerDialog.show()

    }

    private fun timePicker() : TimePickerDialog.OnTimeSetListener {

        // listener which is triggered when the
        // time is picked from the time picker dialog
        val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =

            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                // logic to properly handle
                // the picked timings by user
                val formattedTime: String = when {
                    hourOfDay == 0 -> {
                        if (minute < 10) {
                            "${hourOfDay + 12}:0${minute} am"
                        } else {
                            "${hourOfDay + 12}:${minute} am"
                        }
                    }
                    hourOfDay > 12 -> {
                        if (minute < 10) {
                            "${hourOfDay - 12}:0${minute} pm"
                        } else {
                            "${hourOfDay - 12}:${minute} pm"
                        }
                    }
                    hourOfDay == 12 -> {
                        if (minute < 10) {
                            "${hourOfDay}:0${minute} pm"
                        } else {
                            "${hourOfDay}:${minute} pm"
                        }
                    }
                    else -> {
                        if (minute < 10) {
                            "${hourOfDay}:${minute} am"
                        } else {
                            "${hourOfDay}:${minute} am"
                        }
                    }
                }
                time.setText(formattedTime)
            }
        return timePickerDialogListener
    }

    private fun getData(): ArrayList<String> {

        var list = arrayListOf<String>()

        Log.d("mytag", "Button clicked")

        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequestArray(
            this,
            Request.Method.GET,
            "/available-tests",
            null,
            { response ->

                Log.d("mytag", "$response")

                for (i in 0 until  response.length()){

                    val test: JSONObject = response.getJSONObject(i)

                    // get the current student (json object) data
                    list.add(test.getString("test-name"))

                }
                Log.d("mytag", "$list")

            },
            { error ->
                Log.e("mytag", "Error: $error - Status Code = ${error.networkResponse?.statusCode}")
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)

        return list
    }

    private fun getDataCare(): ArrayList<String> {

        val arrayList = arrayListOf<String>()

        Log.d("mytag", "Button clicked")

        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequestArray(
            this,
            Request.Method.GET,
            "/avaiable-healthcare-centers",
            null,
            { response ->

                Log.d("mytag", "$response")

                for (i in 0 until  response.length()){

                    val student: JSONObject = response.getJSONObject(i)

                    // get the current student (json object) data
                    arrayList.add(student.getString("hc_name"))

                }
                Log.d("mytag", "$arrayList")

            },
            { error ->
                Log.e("mytag", "Error: $error - Status Code = ${error.networkResponse?.statusCode}")
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)
        return arrayList
    }

}