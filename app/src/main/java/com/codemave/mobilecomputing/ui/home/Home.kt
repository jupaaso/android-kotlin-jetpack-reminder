package com.codemave.mobilecomputing.ui.home

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codemave.mobilecomputing.R
import com.codemave.mobilecomputing.data.entity.Category
import com.codemave.mobilecomputing.ui.home.categoryPayment.CategoryPayment
import com.codemave.mobilecomputing.ui.home.categoryReminder.CategoryReminder
import com.google.accompanist.insets.systemBarsPadding

/** Calls HomeContent */
@Composable
fun Home(
    viewModel: HomeViewModel = viewModel(),
    navController: NavController
) {
    val viewState by viewModel.state.collectAsState()

    val selectedCategory = viewState.selectedCategory

    if (viewState.categories.isNotEmpty() && selectedCategory != null) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeContent(
                selectedCategory = selectedCategory,
                categories = viewState.categories,
                onCategorySelected = viewModel::onCategorySelected,
                navController = navController
            )
        }
    }
}


@Composable
fun HomeContent(
    selectedCategory: Category,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
    navController: NavController,
) {
    val activity = (LocalContext.current as? Activity)
    //val appBarColor = MaterialTheme.colors.primary.green
    val appBarColor = MaterialTheme.colors.secondary.copy(alpha = 0.87f)

    var editMode by remember { mutableStateOf(false) }  // EDITMODE reminder
    var deleteMode by remember { mutableStateOf(false) } // DELETEMODE reminder

    /** REMINDER HOME SCREEN NEXT */
    /** "Edit" and "+Add" buttons first */
    Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),
        floatingActionButton = {
            val title = if (editMode) "Cancel" else "Edit"
            Column() {
                Row(Modifier.padding(16.dp)) {
                    OutlinedButton(
                        onClick = {
                            editMode = !editMode
                        },
                        modifier = Modifier
                            .height(50.dp),
                        enabled = true,
                        shape = RoundedCornerShape(size = 50.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                    ) {
                        Text(text = title)  // Button text is "Edit" or "Cancel"
                    }

                    if (editMode) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete", //stringResource("Account"))
                                modifier = Modifier.size(24.dp)
                            )}
                    }
                    Spacer(modifier = Modifier.width(70.dp))  // hori-space between buttons

                    /** Press button and open "Reminder" */
                    ExtendedFloatingActionButton(
                        onClick = { navController.navigate("reminder") },
                        modifier = Modifier.padding(all = 0.dp), // vertical space between buttons
                        backgroundColor = Color.Green,
                        contentColor = Color.Black,
                        text = { Text(text = "Add") },
                        icon = {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                        } )

                    /** FloatingActionButton(           // Press button and open "payment"
                        onClick = { navController.navigate("reminder") },
                        modifier = Modifier.padding(all = 20.dp),
                        backgroundColor = Color.Green,
                        contentColor = Color.Black,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            modifier = Modifier
                                .height(50.dp)
                        )
                    } */
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        )  {
            TopAppBar(
                title = {
                    Text(
                        text ="HW2",
                        //text = stringResource("HW2"),
                        //color = MaterialTheme.colors.primary, // Tekee virhean tekstin
                        //color = MaterialTheme.colors.secondary,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .heightIn(max = 24.dp)
                    )
                },
                //backgroundColor = appBarColor,
                  backgroundColor = MaterialTheme.colors.primary,

                actions = {
                    //Spacer(modifier = Modifier.width(4.dp))  // EI VAIKUTA
                    IconButton(onClick = {
                        activity?.finish()
                    }) {
                        Text(
                            text = "LOGOUT",
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(10.dp))  // EI VAIKUTA
                    }
                    Spacer(modifier = Modifier.width(40.dp))  // VAIKUTTAAKO

                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            modifier = Modifier
                                .heightIn(50.dp)
                                .size(50.dp)
                        ) //stringResource("Search")) Ei vaikutusta koolla
                    }
                    Spacer(modifier = Modifier.width(20.dp))  // to right size of Profile icon

                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Account", //stringResource("Account"))
                            modifier = Modifier.size(50.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))  // to right size of Profile icon
                } )

            /** Prints categories on the screen, Code below */
            CategoryTabs(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected
            )

            /** Prints reminder list on the screen, code on "ui->home->CategoryReminder.kt"*/
            CategoryReminder(
                categoryId = selectedCategory.id,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
    /**Scaffold(
        modifier = Modifier.padding(bottom = 24.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "payment") },
                contentColor = Color.Blue,
                modifier = Modifier.padding(all = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ) {
            val appBarColor = MaterialTheme.colors.secondary.copy(alpha = 0.87f)

            HomeAppBar(
                backgroundColor = appBarColor,
            )

            CategoryTabs(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = onCategorySelected,
            )

            CategoryPayment(
                modifier = Modifier.fillMaxSize(),
                categoryId = selectedCategory.id
            )
        }
    }
} */
/**
@Composable
private fun HomeAppBar(
    backgroundColor: Color
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        backgroundColor = backgroundColor,
        actions = {
            IconButton( onClick = {} ) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = stringResource(R.string.search))
            }
            IconButton( onClick = {} ) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = stringResource(R.string.account))
            }
        }
    )
}
*/
/** Prints scrollable categories below TopAppBar */
@Composable
private fun CategoryTabs(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    val selectedIndex = categories.indexOfFirst { it == selectedCategory }
    ScrollableTabRow(
        selectedTabIndex = selectedIndex,
        edgePadding = 24.dp,
        indicator = emptyTabIndicator,
        modifier = Modifier.fillMaxWidth(),
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onCategorySelected(category) }
            ) {
                ChoiceChipContent(
                    text = category.name,
                    selected = index == selectedIndex,
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun ChoiceChipContent(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        color = when {
            selected -> MaterialTheme.colors.secondary.copy(alpha = 0.87f)
            else -> MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        },
        contentColor = when {
            selected -> Color.Black
            else -> MaterialTheme.colors.onSurface
        },
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

private val emptyTabIndicator: @Composable (List<TabPosition>) -> Unit = {}