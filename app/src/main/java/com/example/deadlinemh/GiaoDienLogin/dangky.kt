package com.example.deadlinemh.GiaoDienLogin

import android.R.attr.onClick
import android.R.attr.password
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.deadlinemh.R
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreenn(navController: NavController) {
    val taikhoandn = remember { mutableStateOf("") }
    val matkhau = remember { mutableStateOf("") }
    val nlmatkhau = remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Loginnn()
        Box(modifier = Modifier
            .fillMaxWidth(0.9f)
            .background(Color(0xAAEDE9F2), shape = RoundedCornerShape(16.dp))
            .padding(18.dp)) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DnDkyy(onClick = {navController.navigate("dangnhap")})
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Column(modifier = Modifier.fillMaxWidth()){
                    Nhapthongtinn(taikhoandn, matkhau, nlmatkhau)
                    Spacer(modifier = Modifier.height(16.dp))
                    NutAnn(taikhoandn.value, matkhau.value, nlmatkhau.value, navController)
                }
            }
        }
    }
}
@Composable
fun DnDkyy(onClick: () -> Unit){
    Text(text = "Đăng nhập",
        fontSize = 16.sp,
        modifier = Modifier.clickable { onClick() }
    )

    Text(text = "|",
        fontSize = 20.sp,
        color = Color.Gray,
        modifier = Modifier
            .padding(start = 30.dp, end = 30.dp))

    Text(text = "Đăng ký",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    )
}


@Composable
fun Nhapthongtinn(emailState: MutableState<String>, passwordState: MutableState<String>, agpasswordState: MutableState<String>){


    TextField(
        value = emailState.value,
        onValueChange = {emailState.value = it},
        placeholder =  {Text(text = "Email")},
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
    TextField(
        value = passwordState.value,
        onValueChange = {passwordState.value = it},
        placeholder = {Text(text = "Mật khẩu")},
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
    TextField(
        value = agpasswordState.value,
        onValueChange = {agpasswordState.value = it},
        placeholder = {Text(text = "Nhập lại mật khẩu")},
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
@Composable
fun NutAnn(email: String, password: String, agpassword: String, navController: NavController){
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    Button(
        onClick = {
            if (password != agpassword){
                Toast.makeText(context, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                            navController.navigate("dangkyyy")
                        } else {
                            Toast.makeText(context, "Lỗi: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                  },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4093AA)),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp)
    ){
        Text(
            text = "Đăng ký",
            fontWeight = FontWeight.Bold
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    OutlinedButton(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.logoface),
            contentDescription = "anh logo",
            modifier = Modifier.size(30.dp)
        )
        Text(text = "Đăng ký với Facebook")
    }
}

@Composable
fun Loginnn(){
    Image(painterResource(
        id = R.drawable.anhlogo),
        contentDescription = "login",
        modifier = Modifier.size(280.dp))
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DaufaultPrevieww(){
    val navController = rememberNavController()
    HomeScreenn(navController)
}
