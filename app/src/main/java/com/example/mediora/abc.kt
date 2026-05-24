package com.example.mediora

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.activity.ComponentActivity

class abc : ComponentActivity() {

    private val doctorsList = arrayOf("Dr. Ayoni Dias", "Dr. Sunil Perera", "Dr. K. N. Sharma", "Dr. Anula Wijewardena")

    private lateinit var txtDoctorName: TextView
    private lateinit var edtMessage: EditText
    private lateinit var chatMessagesContainer: LinearLayout
    private lateinit var scrollViewChat: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatpage)

        // UI Components
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val edtSearch = findViewById<EditText>(R.id.edtSearchMeds)
        txtDoctorName = findViewById(R.id.txtDoctorName)
        val btnVideoCall = findViewById<TextView>(R.id.btnVideoCall)
        val btnAttachFile = findViewById<ImageView>(R.id.btnAttachFile)
        edtMessage = findViewById(R.id.edtMessage)
        val btnSendMessage = findViewById<ImageView>(R.id.btnSendMessage)
        val btnVoiceMessage = findViewById<ImageView>(R.id.btnVoiceMessage)

        // Navigation Bar Components
        val layoutBottomNav = findViewById<LinearLayout>(R.id.layoutBottomNav)
        val btnHome = layoutBottomNav.getChildAt(0)
        val btnPharmacy = layoutBottomNav.getChildAt(1)
        val btnEChannel = layoutBottomNav.getChildAt(2)
        val btnAccount = layoutBottomNav.getChildAt(3)

        // Navigation Listeners
        btnHome.setOnClickListener { Toast.makeText(this, "Navigating to Home", Toast.LENGTH_SHORT).show() }
        btnPharmacy.setOnClickListener { Toast.makeText(this, "Navigating to Pharmacy", Toast.LENGTH_SHORT).show() }
        btnEChannel.setOnClickListener { Toast.makeText(this, "Navigating to E-Channeling", Toast.LENGTH_SHORT).show() }
        btnAccount.setOnClickListener { Toast.makeText(this, "Navigating to Account", Toast.LENGTH_SHORT).show() }

        // Existing Logic
        val chatContainer = findViewById<FrameLayout>(R.id.chatContainer)
        scrollViewChat = ScrollView(this)
        chatMessagesContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
        }

        val scrollParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        scrollViewChat.layoutParams = scrollParams
        scrollViewChat.addView(chatMessagesContainer)
        chatContainer.addView(scrollViewChat)

        edtSearch.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            for (doctor in doctorsList) { popup.menu.add(doctor) }
            popup.setOnMenuItemClickListener { item ->
                txtDoctorName.text = item.title.toString()
                edtSearch.setText(item.title.toString())
                true
            }
            popup.show()
        }

        btnVideoCall.setOnClickListener { Toast.makeText(this, "Starting Video Call...", Toast.LENGTH_SHORT).show() }

        btnSendMessage.setOnClickListener {
            val msgText = edtMessage.text.toString().trim()
            if (msgText.isNotEmpty()) {
                addMessageToChat(msgText, isMe = true)
                edtMessage.text.clear()
                edtMessage.postDelayed({
                    addMessageToChat("Hello, I am ${txtDoctorName.text}. How can I help you?", isMe = false)
                }, 1500)
            }
        }

        btnAttachFile.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply { type = "*/*" }
            startActivityForResult(Intent.createChooser(intent, "Choose File"), 101)
        }

        btnVoiceMessage.setOnClickListener { Toast.makeText(this, "Recording...", Toast.LENGTH_LONG).show() }
        btnBack.setOnClickListener { finish() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            data?.data?.let { addMessageToChat("📎 Attached: ${it.lastPathSegment}", isMe = true) }
        }
    }

    private fun addMessageToChat(message: String, isMe: Boolean) {
        val textView = TextView(this).apply {
            text = message
            textSize = 16f
            setPadding(30, 20, 30, 20)
            setTextColor(Color.WHITE)
            setBackgroundResource(android.R.drawable.toast_frame)
            backgroundTintList = android.content.res.ColorStateList.valueOf(if (isMe) Color.parseColor("#2BBED4") else Color.parseColor("#7C51DD"))
        }

        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            setMargins(0, 10, 0, 10)
            gravity = if (isMe) Gravity.END else Gravity.START
        }

        textView.layoutParams = layoutParams
        chatMessagesContainer.addView(textView)
        scrollViewChat.post { scrollViewChat.fullScroll(ScrollView.FOCUS_DOWN) }
    }
}