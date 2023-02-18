package com.codemave.mobilecomputing.ui.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Profile(
    navController: NavController
) {
    //val painter = painterResource(id = R.drawable.juha)
    // notification
    val notification = rememberSaveable { mutableStateOf("") }
    if (notification.value.isNotEmpty()) {
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value =""
    }

    var name = rememberSaveable { mutableStateOf("default name") }
    var username by rememberSaveable { mutableStateOf("default username") }
    var email by rememberSaveable { mutableStateOf("default email") }
    var bio by rememberSaveable { mutableStateOf("default bio") }

    //val name = rememberSaveable { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(10.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.Center
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Cancel",
                    modifier = Modifier.clickable { notification.value = " Cancelled" })
                Text(
                    text = "Save",
                    modifier = Modifier.clickable { notification.value = "Profile updated" })
            }
            Spacer(modifier = Modifier.height(10.dp))

            // Draw profile image
            ProfileImage()

            // This will also work, shows image, finds image from internet, but cannot be changed
            /*AsyncImage(
                model = "https://media.licdn.com/dms/image/C5103AQHqBc48I_yWXA/profile-displayphoto-shrink_800_800/0/1517513537117?e=2147483647&v=beta&t=uj88pQg6QVrTghOc3tN72eEAc7Tpeod0c-U98fZ129U",
                contentDescription = "profile_image",
                modifier = Modifier
                    //.align(Alignment.Horizontal)
                    .size(150.dp)
                    .wrapContentSize()
                    .clickable {  },
                contentScale = ContentScale.Crop
            ) */

            Spacer(modifier = Modifier.height(1.dp))
            Text(text = "Change Profile Picture")
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Name", modifier = Modifier.width(100.dp))
                TextField(
                    value = name.value,
                    onValueChange = { text -> name.value = text },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.White
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Username", modifier = Modifier.width(100.dp))
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.White
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "email", modifier = Modifier.width(100.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.White
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "Bio", modifier = Modifier
                        .width(100.dp)
                        .padding(top = 8.dp)
                )
                TextField(
                    value = bio,
                    onValueChange = { bio = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        textColor = Color.White
                    ),
                    singleLine = false,
                    modifier = Modifier.height(150.dp)
                )
                /**
                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Username:"
                    )
                    Spacer(modifier = Modifier.height(5.dp))



                    Text(
                        text = "Password:",
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "email:",
                        maxLines = 1
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Mobile:",
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                } */
            }
        }
    }
}

@Composable
fun ProfileImage() {
    val imagePath = ""
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberImagePainter(
        if (imageUri.value.isEmpty())         // create "New" drawable "New vector asset"
            com.codemave.mobilecomputing.R.drawable.ic_user                // import com.sampleandroidapp.mobicomp.R
        else
            imageUri.value
    )
    // launch Activity to select image, get content,
    // what ever Uri we receive we place it in image value
    // and painter will be updated with the new value
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imageUri.value = it.toString() }
    }

    /**var image: Painter = painterResource(id = R.drawable.ic_user)
    Image(painter = image,
    contentDescription = ""
    )
    if (imageUri.value.isNotEmpty())         // create "New" drawable "New vector asset"
    image = painterResource(id = imageUri.value)
    Image(painter = image,
    contentDescription = ""                // import com.sampleandroidapp.mobicomp.R
    ) */
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Using card as we need round/rectangle image shape
        Card(
            shape = RectangleShape,
            modifier = Modifier
                .padding(8.dp)
            //.size(150.dp)
        ) { }
    }
}
            // This will also work, shows image, finds image from internet, but cannot be changed
            /**AsyncImage(
                model = "https://media.licdn.com/dms/image/C5103AQHqBc48I_yWXA/profile-displayphoto-shrink_800_800/0/1517513537117?e=2147483647&v=beta&t=uj88pQg6QVrTghOc3tN72eEAc7Tpeod0c-U98fZ129U",
                contentDescription = "profile_image",
                modifier = Modifier
                    //.align(Alignment.Horizontal)
                    .size(150.dp)
                    .wrapContentSize()
                    .clickable { /* TODO later */ },
                contentScale = ContentScale.Crop
            ) */

            /**
            AsyncImage(   // TAMA EI TOIMINUT ENAAN 18.2 KUN TEIN AJURIIN 1.4.3 jutun 2.2.2:sta
                model = imageUri.value,      // this shows image after it has been selected
                contentDescription = "image from photo picker",
                modifier = Modifier
                    .size(200.dp, 200.dp)
                    .clickable { launcher.launch("image/*") },  // enables selecting image
                //.clip(CircleShape),
                contentScale = ContentScale.Crop,
            )

        }
    }
} */