package com.example.vofaz.main.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vofaz.common.model.CategoryTask
import com.example.vofaz.common.model.Task
import com.example.vofaz.common.view.RecyclerListener
import com.example.vofaz.databinding.BtnTaskLayoutBinding

class RvCategory(
    private val context: Context,
    private var categoryList: MutableMap<String, CategoryTask>,
    private var isTodo: Boolean,
    private val callback: RecyclerListener
): RecyclerView.Adapter<RvCategory.ViewHolder>(), RecyclerListener {

    inner class ViewHolder(private val binding: BtnTaskLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged")
        fun bind(category: CategoryTask?) {
            with(binding) {

                if (category == null) {
                    btnContainer.visibility = View.GONE
                } else {
                    btnContainer.visibility = View.VISIBLE

                    btnTxtName.setText(category.name)
                    if (category.isExpanded) {
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
                    val adapter = RvTask(context, tasks, isTodo, callback)

                    rvBtnTask.adapter = adapter
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = BtnTaskLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun refreshRecycler() {
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val keys = categoryList.keys.toList()
        val category = categoryList[keys[position]]
        if (category != null) {
            category.tasks?.let {
                if (it.size > 0) {
                    holder.bind(category)
                } else {
                    holder.bind(null)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}

