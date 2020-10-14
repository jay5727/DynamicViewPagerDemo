package com.jay.viewpagerdemo2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.jay.viewpagerdemo2.R

/**
 * Fragment to inflate while Adding via Dynamic Tab
 * Also useful for inflating Default Page...
 */
class DisplayPurposeFragment : Fragment() {

    companion object {

        private const val ARGS_TITLE = "args_title"

        fun newInstance(title: String): DisplayPurposeFragment {
            val fragment = DisplayPurposeFragment()
            val args = Bundle()
            args.putString(ARGS_TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_display_purpose, container, false)
        val textView: TextView = root.findViewById(R.id.text_slideshow)
        textView.text = "This is ${arguments?.getString(ARGS_TITLE, "")} Fragment"
        return root
    }
}