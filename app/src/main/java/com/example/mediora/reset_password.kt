package com.example.mediora

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var etEmailOrPhone: EditText
    private lateinit var btnSendCode: Button
    private lateinit var otpContainer: LinearLayout
    private lateinit var tvTimer: TextView
    private lateinit var tvResendCode: TextView
    private lateinit var otpBoxes: List<EditText>

    // නව එකතු කිරීම්
    private lateinit var db: AppDatabase
    private lateinit var etNewPass: TextInputEditText
    private lateinit var etConfirmPass: TextInputEditText
    private lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        db = AppDatabase.getDatabase(this)

        etEmailOrPhone = findViewById(R.id.etEmailOrPhone)
        btnSendCode = findViewById(R.id.btnSendCode)
        otpContainer = findViewById(R.id.otpContainer)
        tvTimer = findViewById(R.id.tvTimer)
        tvResendCode = findViewById(R.id.tvResendCode)

        // අලුත් view සොයාගැනීම
        etNewPass = findViewById(R.id.etNewPassword)
        etConfirmPass = findViewById(R.id.etConfirmPassword)
        btnUpdate = findViewById(R.id.btnUpdatePassword)

        tvResendCode.visibility = View.GONE
        otpBoxes = listOf(findViewById(R.id.otp1), findViewById(R.id.otp2), findViewById(R.id.otp3), findViewById(R.id.otp4), findViewById(R.id.otp5))

        setupOtpLogic()

        btnSendCode.setOnClickListener {
            val email = etEmailOrPhone.text.toString().trim()

            if (btnSendCode.text.toString() == "SEND CODE") {
                if (email.isNotEmpty()) {
                    val randomCode = "12345"
                    Toast.makeText(this@ResetPasswordActivity, "Code: $randomCode", Toast.LENGTH_LONG).show()

                    findViewById<View>(R.id.cardInput).visibility = View.GONE
                    otpContainer.visibility = View.VISIBLE
                    tvTimer.visibility = View.VISIBLE
                    btnSendCode.text = "VERIFY CODE"
                    startTimer()
                } else {
                    etEmailOrPhone.error = "Enter email or phone"
                }
            } else {
                val inputCode = otpBoxes.joinToString("") { it.text.toString() }

                if (inputCode == "12345") {
                    Toast.makeText(this@ResetPasswordActivity, "Success! Enter new password.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ResetPasswordActivity, "Invalid Code!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // මුරපදය යාවත්කාලීන කිරීමේ logic එක
        btnUpdate.setOnClickListener {
            val newPass = etNewPass.text.toString()
            val confirmPass = etConfirmPass.text.toString()
            val userEmail = etEmailOrPhone.text.toString().trim()

            if (newPass == confirmPass && newPass.isNotEmpty()) {
                lifecycleScope.launch(Dispatchers.IO) {
                    db.userDao().updatePassword(userEmail, newPass)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ResetPasswordActivity, "Password Updated!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@ResetPasswordActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            }
        }

        tvResendCode.setOnClickListener {
            Toast.makeText(this@ResetPasswordActivity, "Code: 12345", Toast.LENGTH_SHORT).show()
            tvResendCode.visibility = View.GONE
            btnSendCode.isEnabled = true
            startTimer()
        }
    }

    private fun setupOtpLogic() {
        otpBoxes.forEachIndexed { index, editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1 && index < 4) otpBoxes[index + 1].requestFocus()
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    private fun startTimer() {
        object : CountDownTimer(50000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.text = "Time left: ${millisUntilFinished / 1000}s"
            }
            override fun onFinish() {
                tvTimer.text = "Code expired!"
                tvResendCode.visibility = View.VISIBLE
                btnSendCode.isEnabled = false
            }
        }.start()
    }
}