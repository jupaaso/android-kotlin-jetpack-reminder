package com.codemave.mobilecomputing.ui.reminder

import android.content.Context
import android.icu.util.UniversalTimeScale.toLong
import android.os.Build
import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.room.TypeConverter
import com.codemave.mobilecomputing.data.entity.Category
import com.google.accompanist.insets.systemBarsPadding
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.sql.Date
import java.time.LocalDate
import java.util.*

/** fun presents the one-single-Reminder input screen
 * AND at the end saves new reminder content */
/** ESIKUVA Payment function on 006-exercise */

@Composable
fun Reminder(
    onBackPress: () -> Unit,
    viewModel: ReminderViewModel = viewModel(),
    navController: NavController
) {
    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val category = rememberSaveable { mutableStateOf("") }
    val message = rememberSaveable { mutableStateOf("") }
    val location = rememberSaveable { mutableStateOf("") }
    val doDate = rememberSaveable { mutableStateOf( "") }
    val creationDateTime = rememberSaveable { mutableStateOf( "") }
    val creatorId = rememberSaveable { mutableStateOf( "") }
    val seenDateTime = rememberSaveable { mutableStateOf( "") }

    val latlng = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<LatLng>("location_data")
        ?.value

    val context = LocalContext.current
    var sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    //
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            /** TOP BAR */
            TopAppBar {
                IconButton( onClick = onBackPress ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,  // Default to Filled
                        contentDescription = null
                    )
                }
                Text(text = "Reminder")
                    //textAlign = TextAlign.Center)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                /** MESSAGE */
                OutlinedTextField(
                    value = message.value,
                    onValueChange = { message.value = it },
                    label = { Text(text = "Reminder message")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                CategoryListDropdown(
                    viewState = viewState,
                    category = category
                )
                Spacer(modifier = Modifier.height(10.dp))
                /** REMINDER DATE no time */
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = doDate.value,
                        onValueChange = { doDate.value = it },
                        label = { Text(text = "Do date") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier.fillMaxWidth(fraction = 0.5f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    // If location is unknown
                    if (latlng == null) {
                        OutlinedButton(
                            onClick = { navController.navigate("map") },
                            modifier = Modifier.height(55.dp)
                        ) {
                            Text(text = "Payment location")
                        }
                    } else {
                        // If location is known
                        Text(
                            text = "Lat: ${latlng.latitude}, \nLng: ${latlng.longitude}"
                        )
                        System.out.println("latlng.latitude" + latlng.latitude)
                        System.out.println("latlng.longitude" + latlng.longitude)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Spacer(modifier = Modifier.height(10.dp))
                /** BUTTON TO SAVE GIVEN INFORMATION */
                Button(
                    enabled = true,
                    onClick = {
                        // on below line we are creating and initializing
                        // variable for simple date format.
                        val formatedDate = SimpleDateFormat("yyyy-MM-dd").format(Date()) //"2023-02-19"
                        val formatedTime = SimpleDateFormat("HH:mm").format(Date())  // "21:52"
                        val DateTime = "$formatedDate $formatedTime"  // "2023-02-19 21:52"
                        val givenDate = Date() // "Mon Feb 20 00:00:29 GMT+02:00 2023
                        // on below line we are creating a variable for
                        // current date and time and calling a simple
                        // date format in it.
                        //val currentDateTime = sdf.format(Date())

                        /** Data for database */
                        creationDateTime.value = formatedDate // doDate.value //formatedDate
                        seenDateTime.value = formatedDate // formatedDate
                        creatorId.value = "juha"

                        // Username for creatorId string
                        val userString = sharedPreferences.getString("USERNAME_KEY", "")
                        creatorId.value = userString.toString()

                        //seenDateTime.value = doDate.value // formatedDate
                        //val givenDateLong = dateToTimestamp(givenDate)
                        //givenDateLong = dateToTimestamp(Date())

                        System.out.println("message.value: " + message.value)
                        System.out.println("doDate.value: " + doDate.value)
                        System.out.println("doDate: " + doDate)  // MutableState(value=2023-01-01)@267916237
                        System.out.println("Date().toString(): " + Date().toString()) // "Sun Feb 19 21:56:31 GMT+02:00 2023"
                        System.out.println("categoryId: " + category.value)
                        System.out.println("formatedDate: " + formatedDate) // "2023-02-29"
                        System.out.println("seenDateTime.value: " + seenDateTime.value) // "2023-02-29"
                        System.out.println("getCategoryId: " + getCategoryId(viewState.categories, category.value))
                        System.out.println("creatorId: " + creatorId.value)

                        coroutineScope.launch {
                            viewModel.saveReminder(
                                com.codemave.mobilecomputing.data.entity.Reminder(
                                    reminderMessage = message.value,
                                    //reminderLocationX = 63.56,
                                    //reminderLocationY = 55.90,
                                    reminderDateTime = doDate.value,
                                    reminderCreationDateTime = creationDateTime.value, //creationDateTime.value,
                                    reminderCreatorId = creatorId.value,
                                    reminderSeenDateTime = seenDateTime.value, //seenDateTime.value,
                                    reminderCategoryId = getCategoryId(viewState.categories, category.value)
                                )
                            )
                        }
                        onBackPress()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(55.dp)
                ) {
                    Text("Save payment")
                }
            }
        }
    }
}

private fun getCategoryId(categories: List<Category>, categoryName: String): Long {
    return categories.first { category -> category.name == categoryName }.id
}

@Composable
private fun CategoryListDropdown(
    viewState: ReminderViewState,
    category: MutableState<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp // requires androidx.compose.material:material-icons-extended dependency
    } else {
        Icons.Filled.ArrowDropDown
    }

    Column {
        OutlinedTextField(
            value = category.value,
            onValueChange = { category.value = it},
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Category") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            viewState.categories.forEach { dropDownOption ->
                DropdownMenuItem(
                    onClick = {
                        category.value = dropDownOption.name
                        expanded = false
                    }
                ) {
                    Text(text = dropDownOption.name)
                }

            }
        }
    }
}

@TypeConverter
private fun fromTimestamp(value: Long?): Date? {
    return value?.let { Date(it) }
}

@TypeConverter
private fun dateToTimestamp(date: Date?): Long? {
    return date?.time?.toLong()
}
