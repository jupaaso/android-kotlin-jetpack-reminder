package com.codemave.mobilecomputing.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codemave.mobilecomputing.data.entity.Category
import com.codemave.mobilecomputing.data.entity.Payment
import com.codemave.mobilecomputing.data.entity.Reminder

/**
 * The [RoomDatabase] for this app
 */
@Database(
    entities = [Category::class, Payment::class, Reminder::class],
    version = 2,
    exportSchema = false
)
abstract class MobileComputingDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun paymentDao(): PaymentDao
    abstract fun reminderDao(): ReminderDao
}