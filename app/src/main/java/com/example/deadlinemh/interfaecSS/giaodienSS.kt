package com.example.deadlinemh.interfaecSS


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deadlinemh.interfaceApp.phantrencung

@Composable
fun HomeScreenSS() {
    val showMenu = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        // --- Phần tiêu đề ---
        phantrencung(onMenuClick = { showMenu.value = true })

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp, start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "back",
                modifier = Modifier
                    .rotate(-90f)
                    .size(28.dp)
            )
            Spacer(modifier = Modifier.fillMaxWidth(0.2f))
            Text(
                text = "So sánh sản phẩm",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Thêm",
                    modifier = Modifier.size(36.dp),
                    tint = Color(0xFF2196F3)
                )
                Text(
                    text = "Vui lòng chọn 1\nsản phẩm",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            VerticalDivider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Thêm",
                    modifier = Modifier.size(36.dp),
                    tint = Color(0xFF2196F3)
                )
                Text(
                    text = "Vui lòng chọn 1\nsản phẩm",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}








@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShowHomeScreenSS(){
    HomeScreenSS()
}