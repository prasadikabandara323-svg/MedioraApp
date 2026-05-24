package com.example.mediora

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import android.widget.PopupMenu

class ChatPageActivity : ComponentActivity() {

    private val doctorsList = arrayOf("Dr. Ayoni Dias", "Dr. Sunil Perera", "Dr. K. N. Sharma", "Dr. Anula Wijewardena")

    private lateinit var txtDoctorName: TextView
    private lateinit var edtMessage: EditText
    private lateinit var chatMessagesContainer: LinearLayout
    private lateinit var scrollViewChat: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatpage)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val edtSearch = findViewById<EditText>(R.id.edtSearchMeds)
        txtDoctorName = findViewById(R.id.txtDoctorName)
        val btnVideoCall = findViewById<TextView>(R.id.btnVideoCall)
        val btnAttachFile = findViewById<ImageView>(R.id.btnAttachFile)
        edtMessage = findViewById(R.id.edtMessage)
        val btnSendMessage = findViewById<ImageView>(R.id.btnSendMessage)
        val btnVoiceMessage = findViewById<ImageView>(R.id.btnVoiceMessage)


        val chatContainer = findViewById<android.widget.FrameLayout>(R.id.chatContainer)
        scrollViewChat = ScrollView(this)
        chatMessagesContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 20, 20, 20)
        }

        val scrollParams = android.widget.FrameLayout.LayoutParams(
            android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
            android.widget.FrameLayout.LayoutParams.MATCH_PARENT
        )
        scrollViewChat.layoutParams = scrollParams
        scrollViewChat.addView(chatMessagesContainer)
        chatContainer.addView(scrollViewChat)


        edtSearch.setOnClickListener { view ->
            val popup = PopupMenu(this, view)


            for (doctor in doctorsList) {
                popup.menu.add(doctor)
            }


            popup.setOnMenuItemClickListener { item ->
                val selectedDoctor = item.title.toString()
                txtDoctorName.text = selectedDoctor
                edtSearch.setText(selectedDoctor)
                true
            }
            popup.show()
        }

        // Video Call Action
        btnVideoCall.setOnClickListener {
            val currentDoc = txtDoctorName.text.toString()
            Toast.makeText(this, "Starting Video Call with $currentDoc...", Toast.LENGTH_SHORT).show()
        }

        // Send Message Action
        btnSendMessage.setOnClickListener {
            val msgText = edtMessage.text.toString().trim()
            if (msgText.isNotEmpty()) {
                addMessageToChat(msgText, isMe = true)
                edtMessage.text.clear()

                edtMessage.postDelayed({
                    addMessageToChat("Hello, I am ${txtDoctorName.text}. How can I help you today?", isMe = false)
                }, 1500)
            }
        }

        // Attach File Action
        btnAttachFile.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            startActivityForResult(Intent.createChooser(intent, "Choose File"), 101)
        }

        // Voice Message Action
        btnVoiceMessage.setOnClickListener {
            Toast.makeText(this, "Recording your message...", Toast.LENGTH_LONG).show()
        }

        btnBack.setOnClickListener { finish() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            val fileUri: Uri? = data?.data
            if (fileUri != null) {
                addMessageToChat("📎 Attached File: ${fileUri.lastPathSegment}", isMe = true)
            }
        }
    }

    // Chat Bubbles Dynamic Handling
    private fun addMessageToChat(message: String, isMe: Boolean) {
        val textView = TextView(this).apply {
            text = message
            textSize = 16f
            setPadding(30, 20, 30, 20)
            setTextColor(Color.WHITE)
        }

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 10, 0, 10)
        }

        if (isMe) {
            textView.setBackgroundResource(android.R.drawable.toast_frame)
            textView.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#2BBED4"))
            layoutParams.gravity = Gravity.END
        } else {
            textView.setBackgroundResource(android.R.drawable.toast_frame)
            textView.backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#7C51DD"))
            layoutParams.gravity = Gravity.START
        }

        textView.layoutParams = layoutParams
        chatMessagesContainer.addView(textView)
        scrollViewChat.post { scrollViewChat.fullScroll(ScrollView.FOCUS_DOWN) }
    }
}
