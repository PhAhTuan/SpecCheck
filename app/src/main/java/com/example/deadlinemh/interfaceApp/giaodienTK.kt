package com.example.deadlinemh.interfaceApp

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import com.example.deadlinemh.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(
    navController: NavController,
    isDarkMode: MutableState<Boolean>,
    onDarkModeToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val auth = FirebaseAuth.getInstance()
    val db = Firebase.firestore
    val user = auth.currentUser
    val context = LocalContext.current

    var name by rememberSaveable { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(user) {
        if (user != null) {
            Log.d("AccountScreen", "Loading user data for UID: ${user.uid}")
            val userDocRef = db.collection("users").document(user.uid)
            userDocRef.get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        name = document.getString("name") ?: ""
                        textState = TextFieldValue(name)
                        val darkMode = document.getBoolean("isDarkMode") ?: false
                        isDarkMode.value = darkMode
                        onDarkModeToggle(darkMode)
                        Log.d("AccountScreen", "Loaded name: $name, isDarkMode: $darkMode")
                    }
                    isLoading = false
                }
                .addOnFailureListener { e ->
                    isLoading = false
                    Toast.makeText(context, "Lỗi tải dữ liệu: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("AccountScreen", "Error loading user data: ${e.message}")
                }
        } else {
            isLoading = false
            Toast.makeText(context, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show()
            navController.navigate("dangnhap") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tài khoản") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = "Back",
                            modifier = Modifier.graphicsLayer(rotationZ = 90f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.legion5),
                contentDescription = "Ảnh tài khoản",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(60.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (name.isEmpty()) "Vui lòng đặt tên" else name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Đổi tên",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            textState = TextFieldValue(name)
                            showDialog = true
                        }
                        .padding(16.dp)
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), thickness = 1.dp)

                Text(
                    text = "Yêu thích",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            Log.d("Navigation", "Navigating to yeuthich, routes: ${navController.graph}")
                            navController.navigate("yeuthich") }
                        .padding(16.dp),
                )
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), thickness = 1.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onDarkModeToggle(!isDarkMode.value)
                        }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Dark Mode",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Switch(
                        checked = isDarkMode.value,
                        onCheckedChange = { newValue ->
                            onDarkModeToggle(newValue)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), thickness = 1.dp)

                Text(
                    text = "Đăng xuất",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            auth.signOut()
                            navController.navigate("dangnhap") {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            }
                        }
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Đổi tên", color = MaterialTheme.colorScheme.onBackground) },
                    text = {
                        TextField(
                            value = textState,
                            onValueChange = { textState = it },
                            label = { Text("Nhập tên mới", color = MaterialTheme.colorScheme.onBackground) },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                                focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                                unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                name = textState.text
                                if (user != null) {
                                    db.collection("users").document(user.uid)
                                        .set(mapOf("name" to name, "isDarkMode" to isDarkMode.value), SetOptions.merge())
                                        .addOnSuccessListener {
                                            Toast.makeText(context, "Đã lưu tên", Toast.LENGTH_SHORT).show()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(context, "Lỗi lưu tên: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                }
                                showDialog = false
                            }
                        ) {
                            Text("Lưu", color = MaterialTheme.colorScheme.primary)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Hủy", color = MaterialTheme.colorScheme.primary)
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}
