package com.example.vofaz.main.view

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
import com.example.vofaz.common.view.RecyclerListener
import com.example.vofaz.databinding.FragmentContentMainBinding

class ContentFragment: Fragment(R.layout.fragment_content_main), RecyclerListener {
    private var binding: FragmentContentMainBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null
    private lateinit var adapter: RvCategory
    lateinit var categoryTasks: MutableMap<String, CategoryTask>

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContentMainBinding.bind(view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            categoryTasks = Database.categoriesTaskData
        }
        binding?.let {
            with(it) {

                rvMainCategory.layoutManager = LinearLayoutManager(requireContext())

                adapter = RvCategory(
                    view.context,
                    categoryTasks,
                    fragmentAttachListener?.isTodoSelected() ?: false,
                    this@ContentFragment,
                    mainTxtFirst
                )

                rvMainCategory.adapter = adapter



                mainBtnExpand.setOnClickListener {
                    fragmentAttachListener?.expandScreen()
                    val isExpanded = fragmentAttachListener?.isToolbarExpanded() == true
                    if(isExpanded) {
                        mainBtnExpand.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.arrow_bottom))
                    } else {
                        mainBtnExpand.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_arrow_drop_up_24))

                    }
                }
                getData()
            }
        }

        }

    @SuppressLint("NotifyDataSetChanged")
    override fun refreshRecycler() {
        var beRemoved = ""
        for (key in categoryTasks.keys) {
            if (categoryTasks[key]?.tasks?.size == 0) {
                beRemoved = key
            }
        }
        if (beRemoved != "") {
            categoryTasks.remove(beRemoved)
            beRemoved = ""
        }

        if (categoryTasks.isEmpty()) {
            binding?.mainTxtFirst?.visibility = View.VISIBLE
        }
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getData() {
        binding?.let {
            with(it) {
                val database = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Database.categoriesTaskData
                } else {
                    TODO("VERSION.SDK_INT < O")
                }

                if (database.isEmpty()) {
                    mainTxtFirst.visibility = View.VISIBLE
                    rvMainCategory.visibility = View.GONE
                } else {
                    mainTxtFirst.visibility = View.GONE
                    rvMainCategory.visibility = View.VISIBLE

                    database.forEach { (key, category) ->
                        categoryTasks[key] = category
                    }
                    adapter.notifyDataSetChanged()
                }
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