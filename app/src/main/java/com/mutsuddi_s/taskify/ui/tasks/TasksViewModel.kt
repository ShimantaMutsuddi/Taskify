package com.mutsuddi_s.taskify.ui.tasks


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mutsuddi_s.taskify.data.PreferencesManager
import com.mutsuddi_s.taskify.data.SortOrder
import com.mutsuddi_s.taskify.data.Task
import com.mutsuddi_s.taskify.data.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel
@Inject constructor(
    private val taskDao:TaskDao,
    private val preferencesManager: PreferencesManager
):ViewModel() {

    val searchQuery= MutableStateFlow("")


    val preferencesFlow = preferencesManager.preferencesFlow
    private val tasksEventChannel = Channel<TasksEvent>()
    val tasksEvent = tasksEventChannel.receiveAsFlow()

    private val tasksFlow = combine(
        searchQuery,
        preferencesFlow
    ){ query,filterPreferences ->
        Pair(query, filterPreferences)
    }.flatMapLatest { ( query, filterPreferences) ->
        taskDao.getTasks (query,filterPreferences.sortOrder,filterPreferences.hideCompleted)
    }



    val tasks=tasksFlow.asLiveData()

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onHideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }

    fun onTaskCheckedChanged(task: Task, isChecked: Boolean)=viewModelScope.launch {
            taskDao.update(task.copy(completed = isChecked)) }


    fun onTaskSelected(task: Task) {

    }

     fun onTaskSwiped(task: Task) =viewModelScope.launch {
         taskDao.delete(task)
         tasksEventChannel.send(TasksEvent.ShowUndoDeleteTaskMessage(task))
     }

    fun onUndoDeleteClick(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
    }

    sealed class TasksEvent {
        object NavigateToAddTaskScreen : TasksEvent()
        data class NavigateToEditTaskScreen(val task: Task) : TasksEvent()
        data class ShowUndoDeleteTaskMessage(val task: Task) : TasksEvent()
        data class ShowTaskSavedConfirmationMessage(val msg: String) : TasksEvent()
        object NavigateToDeleteAllCompletedScreen : TasksEvent()
    }



}





//enum class SortOrder{ BY_NAME,BY_DATE}