package com.codemave.mobilecomputing.ui.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codemave.mobilecomputing.Graph
import com.codemave.mobilecomputing.data.entity.Category
import com.codemave.mobilecomputing.data.entity.Reminder
import com.codemave.mobilecomputing.data.repository.CategoryRepository
import com.codemave.mobilecomputing.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository,
    private val categoryRepository: CategoryRepository = Graph.categoryRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(ReminderViewState())

    val state: StateFlow<ReminderViewState>
        get() = _state

    /** save, remove and edit functions f */
    suspend fun saveReminder(reminder: Reminder): Long {
        return reminderRepository.addReminder(reminder)
    }
    suspend fun removeReminder(reminder: Reminder): Int {
        return reminderRepository.deleteReminder(reminder)
    }
    suspend fun editReminder(reminder: Reminder) {
        return reminderRepository.updateReminder(reminder)
    }

    init {
        viewModelScope.launch {
            categoryRepository.categories().collect { list ->
                _state.value = ReminderViewState(
                    categories = list
                )
            }
        }
    }
}

data class ReminderViewState(
    val categories: List<Category> = emptyList()
)