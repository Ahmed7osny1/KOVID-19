package com.sriyank.a3rdmedicalsummertrainingproject.UI

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequest
import kotlinx.android.synthetic.main.activity_con_register.*
import org.json.JSONObject
import java.util.*
import kotlin.properties.Delegates

class ConRegisterActivity : AppCompatActivity() {

    var age :Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_con_register)

        BOD.setOnClickListener {
            calenderShow()
        }

        registerBtn.setOnClickListener { RegisterData() }

        backBtn.setOnClickListener {onBackPressed()}

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

                BOD.setText("" + dayOfMonth + " - " + (monthOfYear+1) + " - " + year)

                age = today.get(Calendar.YEAR) - year

            }, year, month, day)

        if (today.get(Calendar.DAY_OF_YEAR) < calendar.get(Calendar.DAY_OF_YEAR))
        {
            age--
        }

        if(age < 15){
            BOD.text?.clear()
            Toast.makeText(this,"Age should be greater than 15",Toast.LENGTH_LONG).show()
        }

        //currentTimeMillis
        datePickerDialog.show()

    }

    private fun RegisterData(){


        if(validateDate()) {

            // data we send in the request: Email and password
            val params = JSONObject()

            params.put("patient_first_name", intent.getStringExtra("FirstName"))
            params.put("patient_last_name", intent.getStringExtra("LastName"))
            params.put("patient_age", age)
            params.put("patienet_address", addressInputEditText.text.toString())
            params.put("patient_email", intent.getStringExtra("email"))
            params.put("patient_SSN", assn.text.toString())
            params.put("patient_phone", phoneNum.text.toString())
            params.put("patient_date_of_birth",BOD.text.toString())
            params.put("patient_password", intent.getStringExtra("password"))


            Log.d("mytag", "Button clicked")

            // send request
            val queue = Volley.newRequestQueue(this)
            val request = MyRequest(
                this,
                Request.Method.POST,
                "/register",
                params,
                { response ->

                    Log.d("mytag", "response = $response")

                    // goto Login activity
                    val intent = Intent(this, LoginActivity::class.java)
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

    private fun validateDate(): Boolean {

        if((assn.text.toString().isNotEmpty() && assn.text.toString().length == 14 ) &&
            (phoneNum.text.toString().isNotEmpty() && phoneNum.text.toString().length == 11) &&
            BOD.text.toString().isNotEmpty()
            && addressInputEditText.text.toString().isNotEmpty() && age > 15) {
            return true
        } else {

            if (assn.text.toString().isEmpty())
                Toast.makeText(this, "Enter your SSN", Toast.LENGTH_LONG).show()
            else if (assn.text.toString().length != 14)
                Toast.makeText(this, "Enter your Valid SSN (14 Number)", Toast.LENGTH_LONG).show()


            if (phoneNum.text.toString().isEmpty())
                Toast.makeText(this, "Enter your Phone", Toast.LENGTH_LONG).show()
            else if(phoneNum.text.toString().length != 11)
                Toast.makeText(this, "Enter a valid Phone", Toast.LENGTH_LONG).show()

            if(BOD.text.toString().isEmpty())
                Toast.makeText(this, "Enter your Birth of Date", Toast.LENGTH_LONG).show()

            if (addressInputEditText.text.toString().isEmpty())
                Toast.makeText(this, "Enter your Address", Toast.LENGTH_LONG).show()

            if (age <= 15) {
                BOD.text?.clear()
                Toast.makeText(this, "$age Age should be greater than 15", Toast.LENGTH_LONG).show()
            }

            return false
        }
    }
}