package com.sriyank.a3rdmedicalsummertrainingproject.reservation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.transition.Visibility
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.HomeActivity
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.UI.ProfileDoctorActivity
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyConfig
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequest
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequestArray
import kotlinx.android.synthetic.main.activity_dose_reserved.*
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONObject

class doseReservedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dose_reserved)

        deleteDose.setOnClickListener { doseDelete() }


        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequestArray(
            this,
            Request.Method.GET,
            "/get-dose-reservation",
            null,
            { response ->

                Log.d("mytag", "response = $response")

                if (response.length() == 0){
                    cardView.visibility = View.GONE
                }else {
                    noDose.visibility = View.GONE
                    for (i in 0 until response.length()) {

                        val dose: JSONObject = response.getJSONObject(i)

                        doseName.text = dose.getString("dose_name")
                        doseDate.text = dose.getString("pat_test_date")
                        doseTime.text = dose.getString("pat_test_time")
                        doseLocation.text = dose.getString("hc_name")

                    }
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

    private fun doseDelete() {
        val queue = Volley.newRequestQueue(this)
        val request = MyRequest(
            this,
            Request.Method.DELETE,
            "/delete-dose-reservation",
            null,
            { response ->

                Log.d("mytag", "response = $response")

                if(response.getString("msg") == "deleted successfully"){
                    Toast.makeText(this,"deleted successfully",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,ReservedActivity::class.java))
                }else{
                    Toast.makeText(this,"deleted unsuccessful",Toast.LENGTH_LONG).show()
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
}