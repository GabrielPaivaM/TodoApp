package com.example.todoapp.view.fragment.popup

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.navArgument
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddTodoPopupBinding
import com.example.todoapp.model.TodoData
import com.google.android.material.textfield.TextInputEditText

class AddTodoPopupFragment : DialogFragment() {

    private lateinit var binding: FragmentAddTodoPopupBinding
    private lateinit var listener: DialogNextBtnClickListener
    private var todoData: TodoData? = null

    fun setListener(listener: DialogNextBtnClickListener){
        this.listener = listener
    }

    companion object {
        const val TAG = "AddTodoPopupFragment"
        @JvmStatic
        fun newInstance(taskId: String, task: String) =
            AddTodoPopupFragment().apply {
                arguments = Bundle().apply {
                    putString("taskId", taskId)
                    putString("task", task)
                }
            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTodoPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerEvents()

    }

    private fun registerEvents() {
        onButtonCloseClick()
        onSaveButtonClick()
        updateTask()
    }

    private fun updateTask() {
        if (arguments != null) {
            initData()

            binding.todoEt.setText(todoData?.task)
        }
    }

    private fun initData() {
        todoData = TodoData(
            arguments?.getString("taskId").toString(),
            arguments?.getString("task").toString()
        )
    }

    private fun onButtonCloseClick() {
        binding.todoClose.setOnClickListener {
            dismiss()
        }
    }

    private fun onSaveButtonClick() {
        binding.todoNextBtn.setOnClickListener {
            val task = binding.todoEt.text.toString()

            if (task.isNotEmpty() && task.isNotBlank()){
                if (todoData == null) {
                    listener.onSaveTask(task, binding.todoEt)
                } else {
                    todoData!!.task = task
                    listener.onUpdateTask(todoData!!, binding.todoEt)
                }
            } else {
                Toast.makeText(context, "Please type some task!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    interface DialogNextBtnClickListener {
        fun onSaveTask(todo: String, todoEt: TextInputEditText)
        fun onUpdateTask(todoData: TodoData, todoEt: TextInputEditText)
    }
}