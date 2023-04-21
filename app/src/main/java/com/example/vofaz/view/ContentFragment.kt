package com.example.vofaz.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.vofaz.R
import com.example.vofaz.databinding.FragmentContentMainBinding

class ContentFragment: Fragment(R.layout.fragment_content_main) {
    private var binding: FragmentContentMainBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContentMainBinding.bind(view)

        binding?.let {
            with(it) {
                mainBtnExpand.setOnClickListener {
                    fragmentAttachListener?.expandScreen()
                    val isExpanded = fragmentAttachListener?.isToolbarExpanded() == true
                    if(isExpanded) {
                        mainBtnExpand.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.arrow_bottom))
                    } else {
                        mainBtnExpand.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.baseline_arrow_drop_up_24))

                    }
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