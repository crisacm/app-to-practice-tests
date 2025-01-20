package com.github.crisacm.todotesting.ui.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.crisacm.core.domain.model.Task
import com.github.crisacm.todotesting.databinding.FragmentHomeBinding
import com.github.crisacm.todotesting.ui.dialogs.AddTaskDialog
import com.github.crisacm.todotesting.ui.screens.home.adapter.ToDoAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var todoAdapter: ToDoAdapter
    private lateinit var completedAdapter: ToDoAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        todoAdapter = ToDoAdapter()
        todoAdapter.onTaskChecked = setOnTaskChecked
        todoAdapter.onLong = setOnLong
        binding.recyclerViewTodo.adapter = todoAdapter
        binding.recyclerViewTodo.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTodo.setHasFixedSize(false)

        completedAdapter = ToDoAdapter()
        completedAdapter.onTaskChecked = setOnTaskChecked
        completedAdapter.onLong = setOnLong
        binding.recyclerViewCompleted.adapter = completedAdapter
        binding.recyclerViewCompleted.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCompleted.setHasFixedSize(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the views
        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            tasks.filter { !it.isCompleted }
                .also {
                    todoAdapter.setData(it)
                }

            tasks.filter { it.isCompleted }
                .also {
                    completedAdapter.setData(it)
                }
        }

        viewModel.getTasks()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                todoAdapter.filter.filter(newText)
                completedAdapter.filter.filter(newText)
                return false
            }
        })

        binding.extFabAdd.setOnClickListener {
            lifecycleScope.launch {
                if (childFragmentManager.findFragmentByTag(AddTaskDialog.TAG) == null) {
                    AddTaskDialog.newInstance { title,  date ->
                        viewModel.addTask(title, date)
                    }.show(childFragmentManager, AddTaskDialog.TAG)
                }
            }
        }
    }

    private val setOnTaskChecked: (Task) -> Unit
        get() = { task ->
            viewModel.updateTask(task)
        }

    private val setOnLong: (Task) -> Unit
        get() = { task ->
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteTask(task)
                }
                .setNegativeButton("No") { _, _ -> }
                .show()
        }
}
