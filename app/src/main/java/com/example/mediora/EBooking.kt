package com.example.mediora

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// නිවැරදි UI වර්ණ (Colors)
val DarkBlue = Color(0xFF1B1D4A)
val PrimaryBlue = Color(0xFF3F51B5)
val LightBackground = Color(0xFFF4FAFC)
val IconBackgroundBlue = Color(0xFF90CAF9)

data class DoctorItem(
    val id: String,
    val name: String,
    val specialty: String,
    val category: String,
    val imageRes: Int
)


data class BookingItemData(
    val id: Int,
    val title: String,
    val icon: ImageVector = Icons.Default.DateRange
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EBookingScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf("Home") }
    var selectedCategory by remember { mutableStateOf("All") }

    var discountText by remember { mutableStateOf("Get 20% Discount!") }
    var discountSubText by remember { mutableStateOf("On your first E-Channeling booking") }


    var searchQuery by remember { mutableStateOf("") }


    val bookingsList = remember {
        mutableStateListOf(
            BookingItemData(1, "Dr. Melisa Amarathilaka - Tomorrow at 10:30 AM", Icons.Default.CheckCircle),
            BookingItemData(2, "Dr. Danindu Jorge - Wednesday at 04:00 PM", Icons.Default.CheckCircle)
        )
    }


    var bookingInput by remember { mutableStateOf("") }
    var editingBookingId by remember { mutableStateOf<Int?>(null) }
    var nextId by remember { mutableStateOf(3) }

    val allDoctors = remember {
        listOf(
            DoctorItem("doc_1", "Dr: Melisa Amarathilaka", "Dermatologist", "Dermatologist", R.drawable.doc1),
            DoctorItem("doc_2", "Dr: Danindu Jorge", "General Practitioner", "General practitioner", R.drawable.doc2),
            DoctorItem("doc_3", "Dr: Renuka Prasad", "General Practitioner", "General practitioner", R.drawable.doc7),
            DoctorItem("doc_4", "Dr: Kasuni Herath", "Dermatologist", "Dermatologist", R.drawable.doc4),
            DoctorItem("doc_5", "Dr: Amali Barthalemiyo", "Dental Surgeon", "Dental surgeon", R.drawable.doc5),
            DoctorItem("doc_6", "Dr: Nimalka Fernando", "Cardiologist", "Cardiologist", R.drawable.doc6)
        )
    }


    val mappedCategoryFromSymptom = remember(searchQuery) {
        val query = searchQuery.lowercase().trim()
        when {
            query.contains("fever") || query.contains("headache") || query.contains("cough") || query.contains("cold") -> "General practitioner"
            query.contains("skin") || query.contains("rash") || query.contains("pimple") || query.contains("itching") -> "Dermatologist"
            query.contains("tooth") || query.contains("teeth") || query.contains("dental") || query.contains("mouth") -> "Dental surgeon"
            query.contains("chest") || query.contains("heart") || query.contains("pain") || query.contains("pressure") -> "Cardiologist"
            else -> null
        }
    }


    val filteredDoctors = allDoctors.filter { doc ->
        val matchesCategory = selectedCategory == "All" || doc.category.equals(selectedCategory, ignoreCase = true)

        val matchesSearch = if (searchQuery.isBlank()) {
            true
        } else if (mappedCategoryFromSymptom != null) {

            doc.category.equals(mappedCategoryFromSymptom, ignoreCase = true)
        } else {

            doc.name.contains(searchQuery, ignoreCase = true) || doc.specialty.contains(searchQuery, ignoreCase = true)
        }

        matchesCategory && matchesSearch
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HeaderSection(navController = navController)


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(25.dp)),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            text = "Search symptoms (e.g., fever, headache)...",
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
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

            FeaturedDoctorCards(doctors = filteredDoctors, navController = navController)

            SectionTitle("Find a doctor with Specialist")
            CategoryRow(
                selectedCategory = selectedCategory,
                onCategorySelect = { category ->
                    selectedCategory = if (selectedCategory == category) "All" else category
                }
            )

            TopDoctorsHeader()
            TopDoctorsList(doctors = allDoctors, navController = navController)

            PromoBannerSection(
                title = discountText,
                subTitle = discountSubText,
                onUpdateClick = {
                    discountText = "Get 35% Mega Discount for your first booking!"
                    discountSubText = "Special next month offer updated just now!"
                }
            )

            SectionTitle("Upcoming Appointment")
            UpcomingAppointmentCard()

            // 🎯 E-CHANNELING BOOKINGS SECTION WITH CRUD 🎯
            SectionTitle("E-Channeling Bookings Management")

            // ➕/✏️ Create & Update Input Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = bookingInput,
                        onValueChange = { bookingInput = it },
                        placeholder = { Text("Enter doctor name or time...", fontSize = 13.sp) },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                        ),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (bookingInput.isNotBlank()) {
                                if (editingBookingId != null) {
                                    val index = bookingsList.indexOfFirst { it.id == editingBookingId }
                                    if (index != -1) {
                                        bookingsList[index] = bookingsList[index].copy(title = bookingInput)
                                    }
                                    editingBookingId = null
                                } else {

                                    bookingsList.add(BookingItemData(nextId, bookingInput))
                                    nextId++
                                }
                                bookingInput = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(if (editingBookingId != null) "Save" else "Add", fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))


            ChannelingBookingsSection(
                bookings = bookingsList,
                onDeleteClick = { booking ->

                    bookingsList.remove(booking)
                },
                onBookingClick = { booking ->

                    bookingInput = booking.title
                    editingBookingId = booking.id
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BookingBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { newTab -> selectedTab = newTab }
            )
        }
    }
}

@Composable
fun HeaderSection(navController: NavController) {
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
            tint = DarkBlue
        )
        Text(
            text = "E-Booking",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = DarkBlue
        )
        Image(
            painter = painterResource(id = R.drawable.notifi),
            contentDescription = "Notification",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable { /* Action */ },
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun FeaturedDoctorCards(doctors: List<DoctorItem>, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (doctors.isEmpty()) {
            Text(
                text = "No doctors available",
                color = Color.Gray,
                fontSize = 13.sp,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        } else {
            doctors.forEach { doc ->
                FeaturedDoctorCardItem(doc.id, doc.name, doc.specialty, doc.imageRes, navController, Modifier.width(160.dp))
            }
        }
    }
}

@Composable
fun FeaturedDoctorCardItem(id: String, name: String, specialty: String, imageRes: Int, navController: NavController, modifier: Modifier) {
    Card(
        modifier = modifier
            .height(120.dp)
            .shadow(3.dp, shape = RoundedCornerShape(24.dp))
            .clickable { navController.navigate("your_doctor_screen/$id") },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = IconBackgroundBlue)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 12.dp, top = 8.dp, bottom = 8.dp)
                    .weight(1.1f)
            ) {
                Text(text = name, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold, maxLines = 2)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = specialty, color = Color.White.copy(alpha = 0.85f), fontSize = 10.sp)
            }
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.9f)
                    .clip(RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = PrimaryBlue,
        modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 8.dp)
    )
}

@Composable
fun CategoryRow(selectedCategory: String, onCategorySelect: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CategoryTag("General practitioner", Icons.Default.Add, Color(0xFF00C853), selectedCategory == "General practitioner") { onCategorySelect("General practitioner") }
        CategoryTag("Dental surgeon", Icons.Default.Face, Color(0xFF00B0FF), selectedCategory == "Dental surgeon") { onCategorySelect("Dental surgeon") }
        CategoryTag("Dermatologist", Icons.Default.Face, Color(0xFF00B0FF), selectedCategory == "Dermatologist") { onCategorySelect("Dermatologist") }
        CategoryTag("Cardiologist", Icons.Default.Favorite, Color(0xFFFF1744), selectedCategory == "Cardiologist") { onCategorySelect("Cardiologist") }
    }
}

@Composable
fun CategoryTag(label: String, icon: ImageVector, iconColor: Color, isSelected: Boolean, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) DarkBlue else PrimaryBlue),
        modifier = Modifier
            .height(38.dp)
            .clickable { onClick() }
            .shadow(if (isSelected) 4.dp else 0.dp, shape = RoundedCornerShape(18.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(16.dp))
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}

@Composable
fun TopDoctorsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = PrimaryBlue,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("Top Doctors", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = PrimaryBlue)
        }
        Image(
            painter = painterResource(id = R.drawable.chatbot),
            contentDescription = "Chatbot Icon",
            modifier = Modifier
                .size(30.dp)
                .clickable { /* Action */ }
        )
    }
}

@Composable
fun TopDoctorsList(doctors: List<DoctorItem>, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (doctors.isEmpty()) {
            Text(text = "No top doctors in this category", color = Color.Gray, fontSize = 13.sp, modifier = Modifier.padding(vertical = 15.dp))
        } else {
            doctors.forEach { doc ->
                TopDoctorCardItem(
                    id = doc.id,
                    name = doc.name,
                    specialty = doc.specialty,
                    imageRes = doc.imageRes,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun TopDoctorCardItem(id: String, name: String, specialty: String, imageRes: Int, navController: NavController) {
    Card(
        modifier = Modifier
            .width(210.dp)
            .height(100.dp)
            .shadow(2.dp, shape = RoundedCornerShape(22.dp)),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.85f)
                    .background(IconBackgroundBlue, shape = RoundedCornerShape(topStart = 22.dp, bottomStart = 22.dp))
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 6.dp)
                    .weight(1.15f)
            ) {
                Text(text = name, color = PrimaryBlue, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = specialty, color = DarkBlue, fontSize = 11.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

@Composable
fun PromoBannerSection(title: String, subTitle: String, onUpdateClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .shadow(2.dp, shape = RoundedCornerShape(16.dp))
            .animateContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE0B2))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1.2f)) {
                Text(text = title, color = Color(0xFFE65100), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = subTitle, color = Color.Black.copy(alpha = 0.7f), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onUpdateClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE65100)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    modifier = Modifier.height(28.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Update", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .size(40.dp)
                    .background(Color(0xFFFFF3E0), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Promo Update",
                    tint = Color(0xFFE65100),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun UpcomingAppointmentCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(2.dp, shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(44.dp).background(Color(0xFFE8EAF6), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Notifications, contentDescription = "Alert", tint = PrimaryBlue)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text("Dr. Renuka Prasad", fontWeight = FontWeight.Bold, color = DarkBlue, fontSize = 14.sp)
                Text("Tomorrow at 10:30 AM", color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ChannelingBookingsSection(
    bookings: List<BookingItemData>,
    onDeleteClick: (BookingItemData) -> Unit,
    onBookingClick: (BookingItemData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (bookings.isEmpty()) {
            Text("No bookings found.", color = Color.Gray, fontSize = 13.sp)
        } else {
            bookings.forEach { booking ->
                BookingItem(
                    booking = booking,
                    onDelete = { onDeleteClick(booking) },
                    onEditSelect = { onBookingClick(booking) }
                )
            }
        }
    }
}

@Composable
fun BookingItem(booking: BookingItemData, onDelete: () -> Unit, onEditSelect: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clickable { onEditSelect() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE1F5FE))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(booking.icon, contentDescription = null, tint = Color(0xFF0288D1), modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = booking.title,
                    fontSize = 12.sp,
                    color = DarkBlue,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1
                )
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Booking",
                    tint = Color(0xFFFF5252),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun BookingBottomBar(selectedTab: String, onTabSelected: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        colors = CardColors(
            containerColor = Color(0xFFD0E8F2),
            contentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(R.drawable.home, "Home", selectedTab == "Home") { onTabSelected("Home") }
            BottomNavItem(R.drawable.pharmacy, "Pharmacy", selectedTab == "Pharmacy") { onTabSelected("Pharmacy") }
            BottomNavItem(R.drawable.echannel, "E-Channeling", selectedTab == "E-Channeling") { onTabSelected("E-Channeling") }
            BottomNavItem(R.drawable.account, "Account", selectedTab == "Account") { onTabSelected("Account") }
        }
    }
}

@Composable
fun BottomNavItem(imageResId: Int, label: String, isSelected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = label,
            modifier = if (isSelected) Modifier
                .size(36.dp)
                .background(PrimaryBlue, CircleShape)
                .padding(6.dp)
            else Modifier.size(26.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = label, fontSize = 11.sp, color = PrimaryBlue)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EBookingScreenPreview() {
    val dummyNavController = rememberNavController()
    EBookingScreen(navController = dummyNavController)
}