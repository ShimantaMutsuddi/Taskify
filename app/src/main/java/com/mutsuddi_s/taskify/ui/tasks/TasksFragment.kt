package com.mutsuddi_s.taskify.ui.tasks

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView

import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mutsuddi_s.taskify.R
import com.mutsuddi_s.taskify.databinding.FragmentTasksBinding
import com.mutsuddi_s.taskify.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TasksFragment: Fragment(R.layout.fragment_tasks) {

    private val viewModel:TasksViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=FragmentTasksBinding.bind(view)
        val tasksAdapter=TasksAdapter()

        binding.apply {
            recyclerViewTasks.apply {
                adapter=tasksAdapter
                layoutManager=LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.tasks.observe(viewLifecycleOwner)
        { taskList->
            tasksAdapter.submitList(taskList)
        }
        val menuHost: MenuHost = requireActivity()

        // Add menu items without using the Fragment Menu APIs
        // Note how we can tie the MenuProvider to the viewLifecycleOwner
        // and an optional Lifecycle.State (here, RESUMED) to indicate when
        // the menu should be visible
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.menu_fragment_tasks, menu)
                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView

                searchView.onQueryTextChanged {
                    //update search query
                    viewModel.searchQuery.value=it
                }

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_sort_by_name -> {

                        true
                    }
                    R.id.action_sort_by_date_created -> {

                        true
                    }
                    R.id.action_hide_completed_tasks -> {
                        menuItem.isChecked=!menuItem.isChecked

                        true
                    }
                    R.id.action_delete_all_completed_tasks -> {

                        true
                    }
                   // else -> onMenuItemSelected(menuItem)
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)


    }




}