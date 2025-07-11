package com.example.deadlinemh.interfaceLogin

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit



@Composable
fun HomeScreennn(navController: NavController) {
    val taikhoandn = remember { mutableStateOf("") }
    val matkhau = remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Loginn()
        Box(modifier = Modifier
            .fillMaxWidth(0.9f)
            .background(Color(0xAAEDE9F2), shape = RoundedCornerShape(16.dp))
            .padding(18.dp)) {
            Column(modifier = Modifier.fillMaxWidth()){
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DnDky(onClick = {navController.navigate("dangky")})
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Column(modifier = Modifier.fillMaxWidth()){
                    Nhapthongtin(taikhoandn, matkhau)
                    Spacer(modifier = Modifier.height(16.dp))
                    NutAn(taikhoandn.value, matkhau.value, navController)

                }
            }
        }
    }
}
@Composable
fun DnDky(onClick: () -> Unit){
    Text(text = "Đăng nhập",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
    Text(text = "|",
        fontSize = 20.sp,
        color = Color.Gray,
        modifier = Modifier
        .padding(start = 30.dp, end = 30.dp))
    Text(text = "Đăng ký",
        fontSize = 16.sp,
        modifier = Modifier.clickable { onClick() }
    )
}


@Composable
fun Nhapthongtin(emailState: MutableState<String>, passwordState: MutableState<String>){

    TextField(
        value = emailState.value,
        onValueChange = {emailState.value = it},
        placeholder =  {Text(text = "Số điện thoại / Email")},
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent)
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
            disabledIndicatorColor = Color.Transparent)
    )
}
@Composable
fun NutAn(taikhoan: String, password: String, navController: NavController){
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val token = "568457951029-b5brirnouvh9jkhf4fak5p7d6ii8dmre.apps.googleusercontent.com"
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(token)
            .requestEmail()
            .build()
    }
    val googleSignInClient = remember {
        GoogleSignIn.getClient(context, gso)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(context, "Đăng nhập Google thành công", Toast.LENGTH_SHORT).show()
                        navController.navigate("trangchu")
                    } else {
                        Toast.makeText(context, "Google login thất bại", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: ApiException) {
            Toast.makeText(context, "Google Sign-In thất bại: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
//---------------------------------------------
    val otpState = remember { mutableStateOf("") }
    val verificationId = remember { mutableStateOf("") }

    Column {
        TextField(
            value = otpState.value,
            onValueChange = { otpState.value = it },
            label = { Text("Hãy nhập OTP") },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (taikhoan.isEmpty()){
                    Toast.makeText(context,"Hãy nhập tài khoản, Please",Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if(taikhoan.contains("@gmail.com")) {
                   if(taikhoan.isEmpty()){
                       Toast.makeText(context,"Vui lòng nhập Email/SĐT", Toast.LENGTH_SHORT).show()
                       return@Button
                }
                    auth.signInWithEmailAndPassword(taikhoan.trim(), password.trim())
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT)
                                    .show()
                                navController.navigate("trangchu")
                            } else {
                                Toast.makeText(context, "Tài khoản hoặc mật khẩu sai", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    if(otpState.value.isEmpty()){
                        Toast.makeText(context,"Vui lòng nhập mã OPT", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    val credential = PhoneAuthProvider.getCredential(verificationId.value, otpState.value)
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                                navController.navigate("trangchu")
                            } else {
                                Toast.makeText(context, "OTP không chính xác", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4093AA)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Đăng nhập", fontWeight = FontWeight.Bold)
        }
        if (taikhoan.startsWith("0") && taikhoan.all{it.isDigit()} && taikhoan.length >= 10 && taikhoan.isNotEmpty()){
            Button(
                onClick = {
                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber("+84" + taikhoan.trim())
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(context as Activity)
                        .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                                auth.signInWithCredential(credential)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                                            navController.navigate("trangchu")
                                        }
                                    }
                            }
                            override fun onVerificationFailed(e: FirebaseException) {
                                Toast.makeText(context, "Lỗi: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                            override fun onCodeSent(vid: String, token: PhoneAuthProvider.ForceResendingToken) {
                                verificationId.value = vid
                                Toast.makeText(context, "Đã gửi mã OTP", Toast.LENGTH_SHORT).show()
                            }
                        })
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Gửi mã OTP")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
//---------------------------------------
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Quên mật khẩu",
                color = Color.Gray,
                modifier = Modifier.clickable {}
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
//---------------------------------------
    OutlinedButton(
        onClick = {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.logoface),
            contentDescription = "anh logo",
            modifier = Modifier
                .size(30.dp)
        )
        Text(text = "Đăng nhập với Email")
    }
}
@Composable
fun Loginn(){
    Image(painterResource(
        id = R.drawable.anhlogo),
        contentDescription = "login",
        modifier = Modifier.size(280.dp))
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DaufaultPreview(){
    val navController = rememberNavController()
    HomeScreennn(navController)
}
