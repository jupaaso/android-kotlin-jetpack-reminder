package com.codemave.mobilecomputing.data.repository;

import com.codemave.mobilecomputing.data.entity.Reminder
import com.codemave.mobilecomputing.data.room.ReminderDao
import com.codemave.mobilecomputing.data.room.ReminderToCategory
import kotlinx.coroutines.flow.Flow

/**
 * A data repository for [Reminder] instances
 */

class ReminderRepository(
    private val reminderDao: ReminderDao
) {
    /**
    * Returns a flow containing the list of payments associated with the category with the
    * given [categoryId]
    */
    fun remindersInCategory(categoryId: Long) : Flow<List<ReminderToCategory>> {
        return reminderDao.remindersFromCategory(categoryId)
    }

    /**
     * Add a new [Reminder] to the payment store
     */
    suspend fun addReminder(reminder: Reminder) = reminderDao.insert(reminder)

}

