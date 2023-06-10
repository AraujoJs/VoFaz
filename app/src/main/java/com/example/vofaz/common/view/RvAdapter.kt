package com.example.vofaz.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vofaz.common.model.CategoryTask
import com.example.vofaz.common.model.Task
import com.example.vofaz.databinding.BtnTaskLayoutBinding

class RvAdapter(
    private val context: Context,
    private var categoryList: List<CategoryTask>
): RecyclerView.Adapter<RvAdapter.ViewHolder>() {



    inner class ViewHolder(private val binding: BtnTaskLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(category: CategoryTask) {
            with(binding) {

                btnTxtName.text = context.getString(category.name)
                if(category.isExpanded) {
                    btnExpandedView.visibility = View.VISIBLE

                } else {
                    btnExpandedView.visibility = View.GONE
                }
                btnContainer.setOnClickListener {
                    category.isExpanded = !(category.isExpanded)
                    notifyDataSetChanged()
                }

                val tasks: MutableList<Task> = category.tasks ?: mutableListOf()

                rvBtnTask.layoutManager = LinearLayoutManager(context)
                val adapter = RvTask(context, tasks)

                rvBtnTask.adapter = adapter
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapter.ViewHolder {
        val binding = BtnTaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RvAdapter.ViewHolder, position: Int) {
        val categoryTask = categoryList[position]
        holder.bind(categoryTask)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}

