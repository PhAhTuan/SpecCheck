package com.example.deadlinemh.interfaceApp

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.deadlinemh.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen(navController: NavController, modifier: Modifier = Modifier) {
    var name by rememberSaveable { mutableStateOf("") }
    val isDarkMode = rememberSaveable { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    val backgroundColor = if (isDarkMode.value) Color.DarkGray else Color.White
    val textColor = if (isDarkMode.value) Color.White else Color.Black
    val dividerColor = if (isDarkMode.value) Color.Gray else Color.LightGray

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
                    containerColor = backgroundColor,
                    titleContentColor = textColor,
                    navigationIconContentColor = textColor
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor)
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
                    .background(Color.LightGray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (name.isEmpty()) "Bạn chưa có tên" else name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
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
                    color = textColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            textState = TextFieldValue(name)
                            showDialog = true
                        }
                        .padding(16.dp)
                )
                HorizontalDivider(color = dividerColor, thickness = 1.dp)

                Text(
                    text = "Yêu thích",
                    fontSize = 18.sp,
                    color = textColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("yeuthich") }
                        .padding(16.dp)
                )
                HorizontalDivider(color = dividerColor, thickness = 1.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isDarkMode.value = !isDarkMode.value }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Dark Mode", fontSize = 18.sp, color = textColor)
                    Switch(
                        checked = isDarkMode.value,
                        onCheckedChange = { isDarkMode.value = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            uncheckedThumbColor = Color.Gray
                        )
                    )
                }
                HorizontalDivider(color = dividerColor, thickness = 1.dp)

                Text(
                    text = "Đăng xuất",
                    fontSize = 18.sp,
                    color = textColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("dangxuat") }
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Dialog đổi tên
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Đổi tên", color = textColor) },
                    text = {
                        TextField(
                            value = textState,
                            onValueChange = { textState = it },
                            label = { Text("Nhập tên mới", color = textColor) },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = textColor,
                                unfocusedTextColor = textColor,
                                focusedLabelColor = textColor,
                                unfocusedLabelColor = textColor,
                                focusedIndicatorColor = if (isDarkMode.value) Color.White else Color.Black,
                                unfocusedIndicatorColor = if (isDarkMode.value) Color.Gray else Color.LightGray
                            )
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                name = textState.text
                                showDialog = false
                            }
                        ) {
                            Text("Lưu", color = textColor)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Hủy", color = textColor)
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowGDTK() {
    val navController = rememberNavController()
    AccountScreen(navController)
}
