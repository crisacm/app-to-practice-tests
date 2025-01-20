package com.github.crisacm.todotesting.ui.dialogs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.crisacm.todotesting.R
import com.github.crisacm.todotesting.databinding.DialogAddTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat

class AddTaskDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogAddTaskBinding

    private var onSave: (String, Long?) -> Unit = { title, date -> }
    private var date: Long? = null

    companion object {
        const val TAG = "AddTaskDialog"

        @JvmStatic
        fun newInstance(onSave: (String, Long?) -> Unit) = AddTaskDialog().apply {
            this.onSave = onSave
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddTaskBinding.inflate(inflater, container, false)

        with(binding) {
            buttonAddReminder.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .setSelection(date)
                    .build()

                datePicker.addOnPositiveButtonClickListener {
                    date = it
                    val dateStr: String? = date?.let { SimpleDateFormat.getDateInstance().format(it) }
                    binding.buttonAddReminder.text = dateStr
                    Log.d(TAG, "Date: $dateStr")
                }

                datePicker.show(childFragmentManager, "datePicker")
            }

            buttonSave.setOnClickListener {
                if (textInputEditTextTitle.text.isNullOrEmpty()) {
                    textInputLayoutTitle.error = getString(R.string.title_is_required)
                    return@setOnClickListener
                }

                onSave(
                    binding.textInputEditTextTitle.text.toString(),
                    date
                )
                dismiss()
            }

            buttonCancel.setOnClickListener {
                dismiss()
            }
        }

        return binding.root
    }
}
