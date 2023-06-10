package com.example.vofaz.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vofaz.R
import com.example.vofaz.common.model.CategoryTask
import com.example.vofaz.common.model.Database
import com.example.vofaz.common.view.RvAdapter
import com.example.vofaz.databinding.FragmentContentMainBinding

class ContentFragment: Fragment(R.layout.fragment_content_main) {
    private var binding: FragmentContentMainBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null

    private lateinit var adapter: RvAdapter
    private var categoryTasks = ArrayList<CategoryTask>()

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContentMainBinding.bind(view)
        binding?.let {
            with(it) {

                rvMainBtn.layoutManager = LinearLayoutManager(requireContext())
                adapter = RvAdapter(view.context, categoryTasks)
                rvMainBtn.adapter = adapter



                mainBtnExpand.setOnClickListener {
                    fragmentAttachListener?.expandScreen()
                    val isExpanded = fragmentAttachListener?.isToolbarExpanded() == true
                    if(isExpanded) {
                        mainBtnExpand.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.arrow_bottom))
                    } else {
                        mainBtnExpand.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_arrow_drop_up_24))

                    }
                }

                val category1 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Database.categoryTaskData.elementAt(0)
                } else {
                    TODO("VERSION.SDK_INT < O")
                }


                val category2 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Database.categoryTaskData.elementAt(1)
                } else {
                    TODO("VERSION.SDK_INT < O")
                }

                val category3 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Database.categoryTaskData.elementAt(2)
                } else {
                    TODO("VERSION.SDK_INT < O")
                }


                categoryTasks.add(category1)
                categoryTasks.add(category2)
                categoryTasks.add(category3)

                adapter.notifyDataSetChanged()
            }
        }

        }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListener) {
            fragmentAttachListener = context
        } else {
            throw java.lang.IllegalArgumentException("FragmentAttachListener not found.")
        }
    }
    override fun onDestroy() {
        binding = null
        fragmentAttachListener = null
        super.onDestroy()
    }
}