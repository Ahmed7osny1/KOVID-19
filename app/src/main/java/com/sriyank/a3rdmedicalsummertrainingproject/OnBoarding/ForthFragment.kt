package com.sriyank.a3rdmedicalsummertrainingproject.OnBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.sriyank.a3rdmedicalsummertrainingproject.R
import kotlinx.android.synthetic.main.fragment_forth.view.*

class ForthFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_forth, container, false)
        val viewPager =  activity?.findViewById<ViewPager2>(R.id.viewPager)

        view.next4.setOnClickListener {
            viewPager?.currentItem = 4
        }

        return view
    }

}