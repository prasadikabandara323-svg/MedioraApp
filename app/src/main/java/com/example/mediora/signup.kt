package com.example.mediora

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        db = AppDatabase.getDatabase(this)

        val edtName = findViewById<EditText>(R.id.etFullName)
        val edtEmail = findViewById<EditText>(R.id.etEmail)
        val edtPhone = findViewById<EditText>(R.id.etPhone)
        val edtPassword = findViewById<EditText>(R.id.etPassword)
        val edtRePassword = findViewById<EditText>(R.id.etRePassword)
        val btnSignUp = findViewById<Button>(R.id.btnSignUp)
        val tvLoginLink = findViewById<TextView>(R.id.tvLoginLink)

        btnSignUp.setOnClickListener {
            val name = edtName.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val phone = edtPhone.text.toString().trim()
            val password = edtPassword.text.toString()
            val confirmPassword = edtRePassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please ensure all details are filled in!", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "The passwords entered do not match!", Toast.LENGTH_SHORT).show()
            } else {
                val newUser = User(
                    fullName = name, email = email, password = password,
                    phone = phone, age = "", gender = "", height = "",
                    weight = "", bloodGroup = ""
                )

                lifecycleScope.launch(Dispatchers.IO) {
                    db.userDao().insertUser(newUser)

                    withContext(Dispatchers.Main) {
                        val intent = Intent(this@SignupActivity, TellUsMoreActivity::class.java)
                        intent.putExtra("NAME", name)
                        intent.putExtra("USER_EMAIL", email)
                        intent.putExtra("PHONE", phone)
                        intent.putExtra("PASSWORD", password)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

        tvLoginLink.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}