package com.example.vofaz.common.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vofaz.R
import com.example.vofaz.common.model.CategoryTask
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
                    btnExpandedView.setBackgroundResource(R.drawable.baseline_arrow_drop_up_24)

                } else {
                    btnExpandedView.visibility = View.GONE
                    btnExpandedView.setBackgroundResource(R.drawable.arrow_bottom)
                }

                btnContainer.setOnClickListener {
                    category.isExpanded = !(category.isExpanded)
                    notifyDataSetChanged()
                }
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

