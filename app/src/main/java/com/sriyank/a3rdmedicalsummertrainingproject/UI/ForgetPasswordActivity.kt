package com.sriyank.a3rdmedicalsummertrainingproject.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequest
import kotlinx.android.synthetic.main.activity_forget_password.*
import org.json.JSONObject

class ForgetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        repasslottie.visibility = View.VISIBLE

        send.setOnClickListener {

            var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            if(!mEditEmail.text.toString().trim().matches(emailPattern.toRegex()) &&
                mEditEmail.text.toString().isEmpty()){

                if(!mEditEmail.text.toString().trim().matches(emailPattern.toRegex()))
                    Toast.makeText(this, "Enter a valid Email", Toast.LENGTH_LONG).show()
                if(mEditEmail.text.toString().isEmpty())
                    Toast.makeText(this, "Enter your Email", Toast.LENGTH_LONG).show()

            }
            else {
                PasswordForget()
            }

        }

    }
    private fun PasswordForget() {

        val params = JSONObject()
        params.put("email", mEditEmail.text.toString())

        Log.d("mytag", "Button clicked")

        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequest(
            this,
            Request.Method.POST,
            "/send-reset-email",
            params,
            { response ->

                Log.d("mytag", "response = $response")

                if("$response" == "{\"msg\":\"Email sent Successfully\"}") {
                    startActivity(
                        Intent(
                            this,
                            ForgetSuccessfulSendActivity::class.java
                        )
                    )
                }else{
                    Toast.makeText(this, "Email not Registered", Toast.LENGTH_SHORT).show()
                }


                if (response.has("error")) {
                    val errorMesssage = response.getString("error")
                    Toast.makeText(this, "$errorMesssage", Toast.LENGTH_SHORT).show()

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