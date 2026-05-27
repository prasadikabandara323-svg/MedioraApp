
package com.example.mediora

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun YourDoctorScreen(navController: NavController, doctorId: String) {

    val doctor = when (doctorId) {
        "doc_1" -> Doctor(
            id = "doc_1",
            name = "Dr: Melisa Amarathilaka",
            specialty = "Dermatologist",
            imageRes = R.drawable.doc1,
            date = "Monday 27 May 2026",
            time = "4.30 pm",
            location = "Asiri Surgical Hospital (Room 04)",
            totalFee = "Rs. 2,500/=",
            notice = "\"Your appointment time may vary according to doctor's arrival time\"",
            patientsCount = "650+",
            experienceYrs = "12+ Yrs",
            ratingValue = "4.9",
            reviews = listOf(
                Review("Ayushman R.", "Highly recommended! Very caring and took time to explain the treatment."),
                Review("Sanduni Perera", "Great experience. My skin allergy cleared up within a week. Thank you doctor!")
            )
        )
        "doc_2" -> Doctor(
            id = "doc_2",
            name = "Dr: Danindu Jorge",
            specialty = "General Practitioner",
            imageRes = R.drawable.doc2,
            date = "Wednesday 21 June 2026",
            time = "09.00 am",
            location = "Nawaloka Hospital (Room 12)",
            totalFee = "Rs. 1,800/=",
            notice = "\"Fast for at least 8 hours if blood tests are required.\"",
            patientsCount = "1000+",
            experienceYrs = "15+ Yrs",
            ratingValue = "4.8",
            reviews = listOf(
                Review("Nimali Silva", "Very friendly doctor. Prescribed the exact medicine needed and recovered fast."),
                Review("Ruwan Jayasekara", "He listens patiently to all our concerns. Highly professional service.")
            )
        )
        "doc_3" -> Doctor(
            id = "doc_3",
            name = "Dr: Renuka Prasad",
            specialty = "General Practitioner",
            imageRes = R.drawable.doc7,
            date = "Tomorrow 19 May 2026",
            time = "10.30 am",
            location = "Durdans Hospital (Room 02)",
            totalFee = "Rs. 2,000/=",
            notice = "\"Bring all your previous medical reports and prescriptions.\"",
            patientsCount = "420+",
            experienceYrs = "8+ Yrs",
            ratingValue = "4.7",
            reviews = listOf(
                Review("Kamal Perera", "Excellent service, very punctual with times. Highly satisfied!"),
                Review("Anura Fernando", "Good sorting of channel patients, and no long delays at all.")
            )
        )
        "doc_4" -> Doctor(
            id = "doc_4",
            name = "Dr: Kasuni Herath",
            specialty = "Dermatologist",
            imageRes = R.drawable.doc4,
            date = "Friday 23 July 2026",
            time = "6.00 pm",
            location = "Lanka Hospitals (Room 08)",
            totalFee = "Rs. 3,000/=",
            notice = "\"Skin allergy tests might take an additional 30 minutes.\"",
            patientsCount = "380+",
            experienceYrs = "6+ Yrs",
            ratingValue = "4.6",
            reviews = listOf(
                Review("Dilini Wijesinghe", "She carefully analyzed my skin issue and guided me well. Great experience."),
                Review("Kavindi R.", "Very pleasant lady doctor, nicely explained what to avoid for skin rashes.")
            )
        )
        "doc_5" -> Doctor(
            id = "doc_5",
            name = "Dr: Amali Barthalemiyo",
            specialty = "Dental Surgeon",
            imageRes = R.drawable.doc5,
            date = "Saturday 02 June 2026",
            time = "2.00 pm",
            location = "Central Hospital (Dental Unit - Room 01)",
            totalFee = "Rs. 3,500/=",
            notice = "\"Your appointment time may vary according to doctor's arrival time\"",
            patientsCount = "500+",
            experienceYrs = "10+ Yrs",
            ratingValue = "4.9",
            reviews = listOf(
                Review("Sahan K.", "The dental treatment was completely painless. Highly professional doctor!"),
                Review("Malkanthi Alwis", "Very clean setup, and she treats kids so gently during dental extractions.")
            )
        )
        else -> Doctor(
            id = "doc_6",
            name = "Dr: Nimalka Fernando",
            specialty = "Cardiologist",
            imageRes = R.drawable.doc6,
            date = "Monday 31 May 2026",
            time = "9.30 pm",
            location = "Asiri Surgical Hospital (Room 10)",
            totalFee = "Rs. 4,500/=",
            notice = "\"X-ray charges are not included in the initial channeling fee.\"",
            patientsCount = "1200+",
            experienceYrs = "20+ Yrs",
            ratingValue = "5.0",
            reviews = listOf(
                Review("Pathum N.", "One of the best cardiologists in Sri Lanka. Explained everything very clearly."),
                Review("Siriwardena B.", "Saved my father's life. Words cannot express our gratitude to Dr. Nimal.")
            )
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE3F2FD))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // ⬅️ Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { navController.popBackStack() },
                    tint = Color(0xFF0D47A1)
                )

                Text(
                    text = "Your Doctor",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A237E)
                )

                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.size(36.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "✉️", fontSize = 18.sp)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .size(200.dp)
                        .shadow(4.dp, CircleShape),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Image(
                        painter = painterResource(id = doctor.imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = doctor.specialty,
                        color = Color(0xFF1E88E5),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = doctor.name,
                        color = Color(0xFF1A237E),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // 🌟 Stats Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Patients", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                            Text(text = doctor.patientsCount, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A237E))
                        }
                        Box(modifier = Modifier.width(1.dp).height(25.dp).background(Color(0xFFE0E0E0)))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Exp.", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                            Text(text = doctor.experienceYrs, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A237E))
                        }
                        Box(modifier = Modifier.width(1.dp).height(25.dp).background(Color(0xFFE0E0E0)))
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Rating", fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = doctor.ratingValue, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A237E))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFF0F0F0)))
                    Spacer(modifier = Modifier.height(18.dp))

                    // 📅 ⏰ Date & Time
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "📅 ${doctor.date}",
                            color = Color(0xFF1A237E),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "🕒 ${doctor.time}",
                            color = Color(0xFF1A237E),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 📍 Location
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "📍", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Location: ",
                            color = Color(0xFF1A237E),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = doctor.location,
                            color = Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 💵 Total Fee
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "💵", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Total Fee: ",
                                color = Color(0xFF1A237E),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = doctor.totalFee,
                                color = Color(0xFF2E7D32),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .background(Color(0xFFFFEBEE), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Only 3 Slots Left!",
                                color = Color(0xFFD32F2F),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Special Notes
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Special Notes:",
                            color = Color(0xFF1A237E),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = doctor.notice,
                            color = Color(0xFF03A9F4),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // ⭐ Patient Reviews
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Patient Reviews",
                            color = Color(0xFF1A237E),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        doctor.reviews.forEach { singleReview ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF4FAFC)),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = singleReview.reviewerName, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A237E))
                                        Row {
                                            repeat(singleReview.ratingStars) {
                                                Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(12.dp))
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = singleReview.reviewText,
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))



                    Spacer(modifier = Modifier.height(20.dp))

                    // 🎯 Confirm & Book Button

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {

                                val safeFee = doctor.totalFee.replace("/", "")
                                navController.navigate(route = "add_new_patient_screen/$safeFee/${doctor.name}")
                            },
                            modifier = Modifier //
                                .weight(1.5f)
                                .height(54.dp)
                                .shadow(4.dp, shape = RoundedCornerShape(18.dp)),
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C63FF))
                        ) {
                            Text(text = "Confirm & Book", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }

                        Button(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .weight(1.0f)
                                .height(54.dp)
                                .shadow(4.dp, shape = RoundedCornerShape(18.dp)),
                            shape = RoundedCornerShape(18.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF5350))
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.White, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "Delete", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun YourDoctorPreview() {
    YourDoctorScreen(navController = rememberNavController(), doctorId = "doc_1")
}