package com.sriyank.a3rdmedicalsummertrainingproject.OnBoarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sriyank.a3rdmedicalsummertrainingproject.R
import kotlinx.android.synthetic.main.fragment_fifth.view.*


class FifthFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_fifth, container, false)

        view.finish.setOnClickListener {
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginActivity)
            onBoardingFinished()
        }

        return view
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

}