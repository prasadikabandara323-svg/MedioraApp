package com.example.mediora

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Password Toggle Setup
        val passwordLayout = findViewById<TextInputLayout>(R.id.passwordLayout)
        val passwordInput = findViewById<TextInputEditText>(R.id.etPassword)
        passwordLayout.isPasswordVisibilityToggleEnabled = true

        db = AppDatabase.getDatabase(this)

        val edtEmail = findViewById<EditText>(R.id.etEmail)
        val edtPassword = findViewById<TextInputEditText>(R.id.etPassword) // ID එක etPassword ලෙසම තබා ඇත
        val btnLogIn = findViewById<Button>(R.id.btnLogin)
        val tvSignUpLink = findViewById<TextView>(R.id.tvSignUp)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgetPassword)

        btnLogIn.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter Email and Password ", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val user = db.userDao().getUserByEmail(email)

                withContext(Dispatchers.Main) {
                    if (user != null && user.password == password) {

                        val sharedPreferences = getSharedPreferences("MedioraPrefs", Context.MODE_PRIVATE)
                        sharedPreferences.edit().putString("LOGGED_USER", email).apply()

                        val intent = Intent(this@LoginActivity, home::class.java)
                        intent.putExtra("USER_EMAIL", email)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid Email or Password!",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        tvSignUpLink.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}