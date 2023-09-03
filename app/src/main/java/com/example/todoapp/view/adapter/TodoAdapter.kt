package com.example.todoapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TaskItemBinding
import com.example.todoapp.model.TodoData

class TodoAdapter(val todoDataList: MutableList<TodoData>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var listener: TodoAdapterClickInterface? = null

    fun setListener(listener: TodoAdapterClickInterface){
        this.listener = listener
    }

    inner class TodoViewHolder(val binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todoDataList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        with(holder){
            with(todoDataList[position]){
                binding.todoTask.text = this.task

                binding.deleteTask.setOnClickListener {
                    listener?.onDeleteTaskBtnClick(this)
                }

                binding.editTask.setOnClickListener {
                    listener?.onEditTaskBtnClick(this)
                }
            }
        }
    }

    interface TodoAdapterClickInterface{
        fun onDeleteTaskBtnClick(todoData: TodoData)
        fun onEditTaskBtnClick(todoData: TodoData)
    }
}