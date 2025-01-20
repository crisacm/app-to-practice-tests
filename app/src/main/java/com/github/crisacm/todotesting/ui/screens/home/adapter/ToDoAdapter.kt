package com.github.crisacm.todotesting.ui.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.crisacm.core.domain.model.Task
import com.github.crisacm.todotesting.databinding.ItemToDoBinding
import java.text.SimpleDateFormat

class ToDoAdapter : ListAdapter<Task, ToDoViewHolder>(
    object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(
            oldItem: Task,
            newItem: Task
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Task,
            newItem: Task
        ): Boolean = oldItem == newItem
    }
), Filterable {

    private var data: List<Task> = emptyList()

    var onTaskChecked: (Task) -> Unit = {}
    var onLong: (Task) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ToDoViewHolder = ToDoViewHolder(
        ItemToDoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ToDoViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), onTaskChecked, onLong)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults? {
                val filteredList = mutableListOf<Task>()
                val charSequenceString = constraint.toString().lowercase()

                if (charSequenceString.isEmpty()) {
                    filteredList.addAll(data)
                } else {
                    data.forEach { task ->
                        if (task.title.lowercase().contains(charSequenceString)) {
                            filteredList.add(task)
                        }
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults?
            ) {
                @Suppress("UNCHECKED_CAST")
                val filteredList = results?.values as? List<Task> ?: return
                submitList(filteredList)
            }
        }
    }

    fun setData(data: List<Task>) {
        this.data = data
        submitList(data)
    }
}

class ToDoViewHolder(
    private val binding: ItemToDoBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        task: Task,
        onTaskChecked: (Task) -> Unit,
        onLong: (Task) -> Unit
    ) {
        binding.materialCheckBox.setOnCheckedChangeListener(null)
        binding.materialCheckBox.isChecked = task.isCompleted
        binding.materialCheckBox.text = task.title
        binding.materialCheckBox.paint.isStrikeThruText = task.isCompleted
        binding.textView.isVisible = task.dueDate != null
        binding.textView.text = task.dueDate?.let { SimpleDateFormat.getDateInstance().format(it) } ?: ""

        binding.textView.setOnClickListener { binding.materialCheckBox.performClick() }
        binding.materialCheckBox.setOnCheckedChangeListener { _, isChecked ->
            onTaskChecked(task.copy(isCompleted = isChecked))
        }

        binding.materialCheckBox.setOnLongClickListener {
            onLong(task)
            true
        }
    }
}
