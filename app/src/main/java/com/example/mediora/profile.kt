package com.example.mediora

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    private lateinit var txtName: TextView
    private lateinit var txtBlood: TextView
    private lateinit var txtAge: TextView
    private lateinit var txtGender: TextView
    private lateinit var txtHeight: TextView
    private lateinit var txtWeight: TextView
    private lateinit var imgProfile: ImageView

    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etBloodGroup: EditText
    private lateinit var etGender: EditText
    private lateinit var etHeight: EditText
    private lateinit var etWeight: EditText

    private var isEditMode = false
    private var imageUri: Uri? = null

    // පින්තූරය තෝරාගන්නා Launcher එක (Permission එකත් සමඟ)
    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val selectedImageUri = result.data?.data
            if (selectedImageUri != null) {
                // Persistent Permission ලබාගැනීම
                contentResolver.takePersistableUriPermission(selectedImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION)

                imageUri = selectedImageUri
                imgProfile.setImageURI(imageUri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnEditProfile = findViewById<Button>(R.id.btnEditProfile)

        txtName = findViewById(R.id.txtName)
        txtBlood = findViewById(R.id.txtBloodGroup)
        txtAge = findViewById(R.id.txtAge)
        txtGender = findViewById(R.id.txtGender)
        txtHeight = findViewById(R.id.txtHeight)
        txtWeight = findViewById(R.id.txtWeight)
        imgProfile = findViewById(R.id.imgProfile)

        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        etBloodGroup = findViewById(R.id.etBloodGroup)
        etGender = findViewById(R.id.etGender)
        etHeight = findViewById(R.id.etHeight)
        etWeight = findViewById(R.id.etWeight)

        btnBack?.setOnClickListener { finish() }

        // පින්තූරය මත ක්ලික් කළ විට gallery විවෘත වීම
        imgProfile.setOnClickListener {
            if (isEditMode) {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                pickImageLauncher.launch(intent)
            }
        }

        btnEditProfile.setOnClickListener {
            if (!isEditMode) {
                txtName.visibility = View.GONE; etName.visibility = View.VISIBLE; etName.setText(txtName.text)
                txtAge.visibility = View.GONE; etAge.visibility = View.VISIBLE; etAge.setText(txtAge.text.toString().replace("Age: ", ""))
                txtBlood.visibility = View.GONE; etBloodGroup.visibility = View.VISIBLE; etBloodGroup.setText(txtBlood.text.toString().replace("Blood Group: ", ""))
                txtGender.visibility = View.GONE; etGender.visibility = View.VISIBLE; etGender.setText(txtGender.text.toString().replace("Gender: ", ""))
                txtHeight.visibility = View.GONE; etHeight.visibility = View.VISIBLE; etHeight.setText(txtHeight.text.toString().replace("Height: ", "").replace(" cm", ""))
                txtWeight.visibility = View.GONE; etWeight.visibility = View.VISIBLE; etWeight.setText(txtWeight.text.toString().replace("Weight: ", "").replace(" kg", ""))

                btnEditProfile.text = "SAVE PROFILE"
                isEditMode = true
            } else {
                saveUserData()
                isEditMode = false
            }
        }

        btnLogout.setOnClickListener {
            val sharedPreferences = getSharedPreferences("MedioraPrefs", MODE_PRIVATE)
            sharedPreferences.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun saveUserData() {
        val sharedPreferences = getSharedPreferences("MedioraPrefs", MODE_PRIVATE)
        val email = sharedPreferences.getString("LOGGED_USER", "") ?: ""

        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(this@ProfileActivity)
            val user = db.userDao().getUserByEmail(email)
            user?.let {
                val updatedUser = it.copy(
                    fullName = etName.text.toString(),
                    age = etAge.text.toString(),
                    bloodGroup = etBloodGroup.text.toString(),
                    gender = etGender.text.toString(),
                    height = etHeight.text.toString(),
                    weight = etWeight.text.toString(),
                    profilePhotoUri = imageUri?.toString() ?: it.profilePhotoUri
                )
                db.userDao().updateUser(updatedUser)
            }
            withContext(Dispatchers.Main) {
                Toast.makeText(this@ProfileActivity, "Updated!", Toast.LENGTH_SHORT).show()
                recreate()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("MedioraPrefs", MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("LOGGED_USER", null)
        if (userEmail != null) loadUserData(userEmail)
    }

    private fun loadUserData(email: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getDatabase(this@ProfileActivity)
            val user = db.userDao().getUserByEmail(email)
            withContext(Dispatchers.Main) {
                user?.let {
                    txtName.text = it.fullName
                    txtBlood.text = "Blood Group: ${it.bloodGroup}"
                    txtAge.text = "Age: ${it.age}"
                    txtGender.text = "Gender: ${it.gender}"
                    txtHeight.text = "Height: ${it.height} cm"
                    txtWeight.text = "Weight: ${it.weight} kg"

                    // පින්තූරය Load කිරීම (Glide සමඟ)
                    if (!it.profilePhotoUri.isNullOrEmpty()) {
                        Glide.with(this@ProfileActivity)
                            .load(Uri.parse(it.profilePhotoUri))
                            .apply(RequestOptions.circleCropTransform())
                            .into(imgProfile)
                    } else {
                        Glide.with(this@ProfileActivity)
                            .load(R.drawable.placeholder_profile)
                            .apply(RequestOptions.circleCropTransform())
                            .into(imgProfile)
                    }
                }
            }
        }
    }
}