package com.example.mediora

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mediora.ui.theme.MedioraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Login වෙලාද බලන්න
        val sharedPreferences = getSharedPreferences("MedioraPrefs", MODE_PRIVATE)
        val loggedUser = sharedPreferences.getString("LOGGED_USER", null)

        if (loggedUser == null) {
            // Login වෙලා නැත්නම් LoginActivity එකට යවන්න
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        } else {
            // Login වෙලා නම් පමණක් Compose Screen ටික පෙන්වන්න
            setContent {
                MedioraTheme {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // මෙතන startDestination එකට පලවෙනියට පෙන්නන්න ඕන screen එක දෙන්න
    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") {
            EBookingScreen(navController = navController)
        }
        composable("your_doctor_screen/{doctorId}") { backStackEntry ->
            val doctorId = backStackEntry.arguments?.getString("doctorId") ?: "doc_1"
            YourDoctorScreen(navController = navController, doctorId = doctorId)
        }
        composable("add_new_patient_screen") {
            AddNewPatientScreen(navController = navController)
        }
    }
}