package com.sriyank.a3rdmedicalsummertrainingproject.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sriyank.a3rdmedicalsummertrainingproject.R
import kotlinx.android.synthetic.main.activity_regester.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regester)

        con.setOnClickListener {

            if(validate()) {

                val intent = Intent(this, ConRegisterActivity::class.java)
                intent.putExtra("FirstName", fname.text.toString())
                intent.putExtra("LastName", lname.text.toString())
                intent.putExtra("email", emailInput.text.toString())
                intent.putExtra("password", passwordInputEditText.text.toString())

                startActivity(intent)
            }

        }

        backBtn.setOnClickListener { onBackPressed() }

    }

    private fun validate(): Boolean {

        var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        if (fname.text.toString().isNotEmpty() && lname.text.toString().isNotEmpty()
            && (emailInput.text.toString().trim().matches(emailPattern.toRegex()) &&
                    emailInput.text.toString().trim().isNotEmpty()) &&
            (passwordInputEditText.text.toString().isNotEmpty() &&
            passwordInputEditText.text.toString().length > 9)
        ) {
            return true
        } else {

            if (!emailInput.text.toString().trim().matches(emailPattern.toRegex()))
                Toast.makeText(this, "Enter a valid Email", Toast.LENGTH_LONG).show()
            else if (emailInput.text.toString().trim().isEmpty())
                Toast.makeText(this, "Enter Email", Toast.LENGTH_LONG).show()

            if (fname.text.toString().isEmpty())
                Toast.makeText(this, "Enter your First Name", Toast.LENGTH_LONG).show()

            if (lname.text.toString().isEmpty())
                Toast.makeText(this, "Enter your Last Name", Toast.LENGTH_LONG).show()

            if(passwordInputEditText.text.toString().isEmpty())
                Toast.makeText(this, "Enter your Password", Toast.LENGTH_LONG).show()
            else if (passwordInputEditText.text.toString().length <= 9)
                Toast.makeText(this, "Enter valid Password > 9", Toast.LENGTH_LONG).show()

            return false
        }
    }
}