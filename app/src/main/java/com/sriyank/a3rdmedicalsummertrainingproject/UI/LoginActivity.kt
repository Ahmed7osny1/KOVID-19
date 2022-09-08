package com.sriyank.a3rdmedicalsummertrainingproject.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.Volley
import com.sriyank.a3rdmedicalsummertrainingproject.HomeActivity
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyConfig
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyRequest
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        goHome.setOnClickListener { loginClicked() }

        noAccountTv.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        forgetTv.setOnClickListener {
            startActivity(Intent(this, ForgetPasswordActivity::class.java))
        }

    }

    private fun loginClicked() {

        //if(validateData()) {
        // data we send in the request: Email and password
        val params = JSONObject()
        params.put("email", mEditEmail.text.toString())
        params.put("password", mEditPassword.text.toString())

        Log.d("mytag", "Button clicked")

        // send request
        val queue = Volley.newRequestQueue(this)
        val request = MyRequest(
            this,
            Request.Method.POST,
            "/login",
            params,
            { response ->

                Log.d("mytag", "response = $response")

                // if token was sent
                if (response.has("token")) {
                    // store token in shared prefs
                    val token = response.getString("token")

                    val prefs = getSharedPreferences(
                        MyConfig.SHARED_PREFS_FILENAME,
                        MODE_PRIVATE
                    )
                    val prefsEditor = prefs.edit()
                    prefsEditor.putString("token", token)
                    prefsEditor.apply()

                    val type = response.getString("user")

                    if (type == "patient") {
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        val intent = Intent(this@LoginActivity,
                                DoctorProfileActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
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