package com.codemave.mobilecomputing.ui.home.categoryReminder

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codemave.mobilecomputing.data.entity.Category
import com.codemave.mobilecomputing.data.entity.Reminder
import com.codemave.mobilecomputing.data.room.ReminderToCategory
import com.codemave.mobilecomputing.ui.reminder.CurrentDateTime
import com.codemave.mobilecomputing.util.viewModelProviderFactoryOf
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.List

/** Reminder on home page
 * This prints the list items */
@Composable
fun CategoryReminder(
    categoryId: Long,               // Category related
    modifier: Modifier = Modifier
) {
    val viewModel: CategoryReminderViewModel = viewModel(
        key = "category_list_$categoryId",
        factory = viewModelProviderFactoryOf { CategoryReminderViewModel(categoryId) }
    )
    val viewState by viewModel.state.collectAsState()

    // Defines layout of reminder list
    Column(Modifier.fillMaxWidth()) {
        ReminderList(
            list = viewState.reminders
        )
    }
}

/** Defines LazyColumn list for ReminderList */
@Composable
private fun ReminderList(
    list: List<ReminderToCategory>
) {
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ) {
        items(list) { item ->
            ReminderListItem(
                reminder = item.reminder,
                category = item.category,
                onClick = { /*editMode*/ /*TODO*/ },
                modifier = Modifier.fillParentMaxWidth()
            )
        }
    }
}
/** ReminderList Item printed/shown in constraintLayout form */
@Composable
private fun ReminderListItem(
    reminder: Reminder,
    category: Category,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // clickable means that the whole payment item line is clickable in list
    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, reminderMessage, reminderDateTime, reminderCategory, icon, date) = createRefs()
        var checked by remember { mutableStateOf(false) }
        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        /** MESSAGE on 1st line */
        Text(
            text = reminder.reminderMessage,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(reminderMessage) {
                linkTo(
                    start = divider.start,
                    end = icon.start,    // ARVAUSHEITETTY
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        /** DATETIME on 2nd line */
        /** Text(
            text = when {
                reminder.reminderDateTime != null -> {
                    reminder.reminderDateTime.formatToString()
                }
                else -> 0
            }, */
        Text(
            //text = SimpleDateFormat("yyyy-MM-dd").format(reminder.reminderDateTime),
            text = reminder.reminderDateTime,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(reminderDateTime) {
                linkTo(
                    start = reminderMessage.start, //parent.start,
                    end = icon.start,
                    startMargin = 0.dp,
                    endMargin = 16.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                    //)
                )
                //centerVerticallyTo(reminderTitle)
                top.linkTo(reminderMessage.bottom, 6.dp)
                //bottom.linkTo (parent.bottom, 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        /** CATEGORY NAME*/
        Text(
            text = category.name,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.constrainAs(reminderCategory) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin = 8.dp,
                    bias = 0f // float this towards the start. this was is the fix we needed
                )
                top.linkTo(reminderDateTime.bottom, margin = 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
                width = Dimension.preferredWrapContent
            }
        )
        /** CHECKBOX at the end of line */
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = !checked },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(icon) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        )

        /** CHECKED ICON tama ei toiminut checkboxina */

        /**IconButton(
            onClick = {
                checked = !checked,
            },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(icon) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Filled.CheckBox,
                contentDescription = "Check"
            )
        } */

    }
}

private fun Date.formatToString(): String {
    return SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(this)
    }

fun Long.toDateString(): String {
    return SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date(this))
}