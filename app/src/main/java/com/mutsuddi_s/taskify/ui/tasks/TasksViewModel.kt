package com.mutsuddi_s.taskify.ui.tasks


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mutsuddi_s.taskify.data.PreferencesManager
import com.mutsuddi_s.taskify.data.SortOrder
import com.mutsuddi_s.taskify.data.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
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
}
//enum class SortOrder{ BY_NAME,BY_DATE}