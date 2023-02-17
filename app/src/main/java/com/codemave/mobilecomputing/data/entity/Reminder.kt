package com.codemave.mobilecomputing.data.entity

import android.graphics.Bitmap
import androidx.room.*
import java.util.*

@Entity(
    tableName = "reminders",            // table name
    indices = [
        Index("id", unique = true),     // First index
        Index("reminder_category_id")   // Second index
    ],
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["reminder_category_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],
    //ignoredColumns = ["picture"]  //https://developer.android.com/training/data-storage/room/defining-data#column-indexing
)

data class Reminder(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val reminderId: Long = 0,
    @ColumnInfo(name = "reminder_category_id") val reminderCategoryId: Long,
    @ColumnInfo(name = "reminder_message") val reminderMessage: String,
    //@ColumnInfo(name = "reminder_location_x") val reminderLocationX: Double,
    //@ColumnInfo(name = "reminder_location_y") val reminderLocationY: Double,
    @ColumnInfo(name = "reminder_datetime") val reminderDateTime: Long,
    //@ColumnInfo(name = "reminder_creation_datetime") val reminderCreationDateTime: Long,
    //@ColumnInfo(name = "reminder_creator_id") val reminderCreatorId: Long,
    //@ColumnInfo(name = "reminder_seen_datetime") val reminderSeenDateTime: Long
    //@Ignore var picture: Bitmap?  EI TOIMI
)

/* Tama oli HW1 palautuksessa
data class Reminder(
    val reminderId: Long,
    val reminderTitle: String,
    val reminderDate: Date?
) */