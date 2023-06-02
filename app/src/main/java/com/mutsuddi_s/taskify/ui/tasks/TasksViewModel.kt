package com.mutsuddi_s.taskify.ui.tasks


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mutsuddi_s.taskify.data.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TasksViewModel
@Inject constructor(
    private val taskDao:TaskDao
):ViewModel() {

    val searchQuery= MutableStateFlow("")

    private val tasksFlow = searchQuery.flatMapLatest {
        taskDao.getTasks(it)
    }

    val tasks=tasksFlow.asLiveData()
}