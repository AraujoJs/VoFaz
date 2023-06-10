package com.example.vofaz.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.vofaz.common.model.Task
import com.example.vofaz.databinding.TaskLayoutBinding
import java.time.format.DateTimeFormatter

class RvTask(
    private val context: Context,
    private var taskList: MutableList<Task>
): RecyclerView.Adapter<RvTask.ViewHolder>() {


    inner class ViewHolder(private val binding: TaskLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("NotifyDataSetChanged")
        fun bind(task: Task) {
            with(binding) {

                taskTxtName.text = task.name
                taskImgIcon.setImageDrawable(ContextCompat.getDrawable(context, task.icon))

                val hour = task.localTime?.format(DateTimeFormatter.ofPattern("hh:mm"))
                taskHour.text = hour

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvTask.ViewHolder {
        val binding = TaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RvTask.ViewHolder, position: Int) {
        val task = taskList[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }
}

