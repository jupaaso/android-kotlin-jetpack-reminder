package com.codemave.mobilecomputing.ui.login

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Login(
    navController: NavController
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        //Text("Welcome to the login screen")                // Welcome text is not needed

        // on below line we are creating
        // a variable for shared preferences.
        lateinit var sharedPreferences: SharedPreferences
        val context = LocalContext.current

        // on below line we are creating a variable
        // for the file MyPefs key and user key and pwd key.

        var PREFS_KEY = "MyPrefs"
        var USERNAME_KEY = "juha"
        var PASSWORD_KEY = "pass"

        // SharedPreferences Initialize
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val username = rememberSaveable { mutableStateOf("") }  // if needed pre-value: juha
        val password = rememberSaveable { mutableStateOf("") }  // if needed pre-value:

        // Not needed
        //val usernameErrorState = remember { mutableStateOf(false) }
        //val passwordErrorState = remember { mutableStateOf(false) }

        // Get the Editor of SharedPreferences
        val editor = sharedPreferences.edit()

        // To sharedPreferences default values
        editor.putString("USERNAME_KEY", USERNAME_KEY)
        editor.putString("PASSWORD_KEY", PASSWORD_KEY)
        editor.commit()

        //val context = LocalContext.current

        Column(
            modifier = Modifier.padding(20.dp),                 // column items left and right sides
            horizontalAlignment = Alignment.CenterHorizontally, // loginpage: horizontally all to middle
            verticalArrangement = Arrangement.Center            // loginpage: vertically to center
        ) {
            //Image(                                   //androidx.compose.foundation.Image
            //    painter = painterResource(id = R.drawable.ic_launcher_foreground),
            //    contentDescription = "login_image",
            //    modifier = Modifier.fillMaxWidth(),
            //    alignment = Alignment.Center
            //)

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))
            // TextField: text moves up inside the box
            OutlinedTextField(                      // Outlined: text moves up to outline
                value = username.value,
                onValueChange = {
                        text -> username.value = text
                    if (username.value.isNotEmpty()) {
                        editor.putString("USERNAME_KEY", username.value)
                        // Save the above values in Preferences
                        editor.apply() }
                },
                modifier = Modifier.fillMaxWidth(),      // needs to do this first
                label = { Text(text = "Username") },       // and then Center the image
                shape = RoundedCornerShape(size = 50.dp) // rounded corners
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = {
                        passwordString -> password.value = passwordString
                    if (password.value.isNotEmpty()) {
                        editor.putString("PASSWORD_KEY", password.value)
                        // Save the above values in Preferences
                        editor.apply() }
                },
                modifier = Modifier.fillMaxWidth(),        // Calling the isolla "head" Modifier
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),  // hides text, shows *
                shape = RoundedCornerShape(size = 50.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(                       // androidx.compose.ui.Modifier
                onClick = {
                    val userStr = sharedPreferences.getString("USERNAME_KEY", "")
                    val passStr = sharedPreferences.getString("PASSWORD_KEY", "")

                    System.out.println("val userStr: " + userStr)
                    System.out.println("val passStr: " + passStr)
                    System.out.println("val username.value: " + username.value)
                    System.out.println("val password.value: " + password.value)

                    if (username.value == userStr!! && password.value == passStr!!) {
                        System.out.println("Silmukassa homeen menossa OLEN")
                        navController.navigate("home") }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = true,
                shape = RoundedCornerShape(size = 50.dp)
            ) {
                Text(text = "Login")
            }
        }
    }
}

/**@Composable
fun Login(
    navController: NavController
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        val username = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = username.value,
                onValueChange = { data -> username.value = data },
                label = { Text("Username")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = password.value,
                onValueChange = { data -> password.value = data },
                label = { Text("Password")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { navController.navigate("home") },
                enabled = true,
                modifier = Modifier.fillMaxWidth().size(55.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Login")
            }
        }
    }
} */