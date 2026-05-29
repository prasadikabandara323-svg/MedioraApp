package com.example.mediora

import android.content.Intent
import android.widget.Toast
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewPatientScreen(
    navController: NavController,
    doctorFee: String,
    doctorName: String
) {
    var patientName by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var reasonForVisit by remember { mutableStateOf("") }
    var previousRecords by remember { mutableStateOf("") }

    var emergencyContact by remember { mutableStateOf("") }
    var nicNumber by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("Male") }
    var expandedGender by remember { mutableStateOf(false) }

    var selectedTab by remember { mutableStateOf("E-Channeling") }
    var searchQuery by remember { mutableStateOf("") }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val database = remember { AppDatabase.getDatabase(context) }

    val lightBlueBg = Color(0xFFF4FAFC)
    val darkBlueText = Color(0xFF1B1D4A)
    val buttonColor = Color(0xFF6C73E6)
    val tableHeaderColor = Color(0xFF4FC3F7)
    val tableRowColor = Color(0xFFB3E5FC)
    val primaryBlue = Color(0xFF3F51B5)

    Scaffold(
        bottomBar = {

            BookingBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(lightBlueBg)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                // --- 1. TOP BAR ---
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE3F2FD))
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(26.dp)
                            .clickable { navController.popBackStack() },
                        tint = darkBlueText
                    )
                    Text(
                        text = "Add New Patient",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = darkBlueText
                    )
                    Image(
                        painter = painterResource(id = R.drawable.notifi),
                        contentDescription = "Notification",
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                // --- 2. SINGLE SEARCH BAR ---
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .shadow(elevation = 3.dp, shape = RoundedCornerShape(25.dp)),
                    shape = RoundedCornerShape(25.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                text = "Search saved patient profiles...",
                                color = Color.LightGray,
                                fontSize = 14.sp
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Icon",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {
                                        if (searchQuery.isNotBlank()) {
                                            coroutineScope.launch {
                                                val foundPatient = database.patientDao().getPatientByName(searchQuery)
                                                if (foundPatient != null) {
                                                    patientName = foundPatient.patientName
                                                    age = foundPatient.ageGender.substringBefore(" ")
                                                    selectedGender = foundPatient.ageGender.substringAfter(" ", "Male")
                                                    contactNumber = foundPatient.contactNumber
                                                    reasonForVisit = foundPatient.reasonForVisit
                                                    previousRecords = foundPatient.previousRecords ?: ""
                                                    Toast.makeText(context, "Patient Profile Loaded!", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    Toast.makeText(context, "No patient found with that name", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        } else {
                                            Toast.makeText(context, "Please enter a name to search", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        textStyle = TextStyle(fontSize = 14.sp, color = Color.Black),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // --- 3. PATIENT DETAILS TABLE ---
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .shadow(1.dp, RoundedCornerShape(4.dp)),
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(tableHeaderColor)
                                .padding(vertical = 10.dp)
                        ) {
                            Text(
                                text = "Patient Details",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 15.sp
                            )
                            Box(modifier = Modifier.width(1.dp).height(20.dp).background(Color.White))
                            Text(
                                text = "Description",
                                modifier = Modifier.weight(1.2f),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                fontSize = 15.sp
                            )
                        }

                        EditableFormRow("Patient Name", patientName, tableRowColor) { input ->
                            if (input.all { it.isLetter() || it.isWhitespace() }) {
                                patientName = input
                            }
                        }

                        EditableFormRow("Age (Yrs)", age, tableRowColor) { input ->
                            if (input.all { it.isDigit() } && input.length <= 2) {
                                age = input
                            }
                        }

                        // Gender Dropdown Row
                        Row(
                            modifier = Modifier.fillMaxWidth().height(50.dp).background(tableRowColor),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Gender",
                                modifier = Modifier.weight(1f).padding(start = 12.dp),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                            Box(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.White))
                            Box(modifier = Modifier.weight(1.2f).fillMaxHeight().clickable { expandedGender = true }.padding(start = 16.dp, top = 14.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(text = selectedGender, fontSize = 13.sp, color = Color.Black)
                                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Black)
                                }
                                DropdownMenu(expanded = expandedGender, onDismissRequest = { expandedGender = false }) {
                                    DropdownMenuItem(text = { Text("Male") }, onClick = { selectedGender = "Male"; expandedGender = false })
                                    DropdownMenuItem(text = { Text("Female") }, onClick = { selectedGender = "Female"; expandedGender = false })
                                    DropdownMenuItem(text = { Text("Other") }, onClick = { selectedGender = "Other"; expandedGender = false })
                                }
                            }
                        }
                        HorizontalDivider(color = Color.White, thickness = 1.dp)

                        EditableFormRow("NIC / Passport", nicNumber, tableRowColor) { input ->
                            if (input.all { it.isDigit() } && input.length <= 12) {
                                nicNumber = input
                            }
                        }

                        EditableFormRow("Contact Number", contactNumber, tableRowColor) { input ->
                            if (input.all { it.isDigit() } && input.length <= 10) {
                                contactNumber = input
                            }
                        }

                        EditableFormRow("Emergency Contact", emergencyContact, tableRowColor) { input ->
                            if (input.all { it.isDigit() } && input.length <= 10) {
                                emergencyContact = input
                            }
                        }

                        EditableFormRow("Reason for Visit", reasonForVisit, tableRowColor) { reasonForVisit = it }
                        EditableFormRow("Previous Records(Optional)", previousRecords, tableRowColor) { previousRecords = it }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // --- 4. ACTION BUTTONS ---
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            if (patientName.isNotBlank() && contactNumber.isNotBlank()) {
                                val currentPatientName = patientName
                                val currentDocName = doctorName
                                val currentDocFee = doctorFee
                                val currentContact = contactNumber

                                coroutineScope.launch {
                                    val newPatient = Patient(
                                        patientName = currentPatientName,
                                        ageGender = "$age $selectedGender",
                                        contactNumber = contactNumber,
                                        reasonForVisit = reasonForVisit,
                                        previousRecords = previousRecords,
                                        emergencyContact = emergencyContact,
                                        nicNumber = nicNumber
                                    )
                                    database.patientDao().insertPatient(newPatient)
                                    Toast.makeText(context, "Patient Saved Successfully!", Toast.LENGTH_SHORT).show()

                                    patientName = ""
                                    age = ""
                                    contactNumber = ""
                                    reasonForVisit = ""
                                    previousRecords = ""
                                    emergencyContact = ""
                                    nicNumber = ""

                                    val intent = Intent(context, PaymentActivity::class.java).apply {
                                        putExtra("DOCTOR_NAME", currentDocName)
                                        putExtra("DOCTOR_FEE", currentDocFee)
                                        putExtra("PATIENT_NAME", currentPatientName)
                                        putExtra("CONTACT_NUMBER", currentContact)
                                    }
                                    context.startActivity(intent)
                                }
                            } else {
                                Toast.makeText(context, "Please fill Name and Contact Number", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f).height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text(text = "Save & Pay", fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Medium)
                    }

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(42.dp)
                            .background(Color(0xFF26C6DA), CircleShape)
                            .clickable {
                                patientName = ""
                                age = ""
                                contactNumber = ""
                                reasonForVisit = ""
                                previousRecords = ""
                                emergencyContact = ""
                                nicNumber = ""
                                Toast.makeText(context, "Form Cleared!", Toast.LENGTH_SHORT).show()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Clear", tint = Color.White, modifier = Modifier.size(24.dp))
                    }

                    Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.weight(1f).height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEF5350)
                        ),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text(text = "Delete", fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                // --- 5. IMPORTANT INSTRUCTIONS CARD ---
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .shadow(2.dp, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.echaneling),
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "📢 Important Instructions:",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "• Make sure to enter the correct contact number for SMS updates.\n" +
                                    "• Optional previous records can include allergies or long-term drugs.\n" +
                                    "• Double-check your data before pressing Save & Pay.",
                            fontSize = 12.sp,
                            color = Color.DarkGray,
                            lineHeight = 18.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditableFormRow(label: String, value: String, bgColor: Color, onValueChange: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp).background(bgColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier.weight(1f).fillMaxHeight().padding(start = 12.dp, top = 14.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Box(modifier = Modifier.width(1.dp).fillMaxHeight().background(Color.White))
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1.2f).fillMaxHeight(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = bgColor,
                unfocusedContainerColor = bgColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = 13.sp, color = Color.Black)
        )
    }
    HorizontalDivider(color = Color.White, thickness = 1.dp)
}