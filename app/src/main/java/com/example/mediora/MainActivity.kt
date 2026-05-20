package com.example.mediora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mediora.ui.theme.MedioraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedioraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {

                        val navController = rememberNavController()

                        NavHost(
                            navController = navController,
                            startDestination = "home_screen"
                        ) {
                            // 🏠 E-Booking
                            composable("home_screen") {
                                EBookingScreen(navController = navController)
                            }

                            // 🩺 Doctor
                            composable("your_doctor_screen/{doctorId}") { backStackEntry ->
                                val doctorId = backStackEntry.arguments?.getString("doctorId") ?: "doc_1"
                                YourDoctorScreen(navController = navController, doctorId = doctorId)
                            }

                            // 📝 Patient
                            composable("add_new_patient_screen") {
                                AddNewPatientScreen(navController = navController)
                            }
                        }

                    }
                }
            }
        }
    }
}