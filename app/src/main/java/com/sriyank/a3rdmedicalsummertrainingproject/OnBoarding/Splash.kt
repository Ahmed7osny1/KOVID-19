package com.sriyank.a3rdmedicalsummertrainingproject.OnBoarding

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.sriyank.a3rdmedicalsummertrainingproject.R
import com.sriyank.a3rdmedicalsummertrainingproject.Utils.MyConfig

class Splash : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Handler().postDelayed({

            if(checkLoggedIn()){
                val prefs = requireActivity().getSharedPreferences(
                    MyConfig.SHARED_PREFS_FILENAME,
                    AppCompatActivity.MODE_PRIVATE
                )
                val type = prefs.getString("type", null)
                if(type == "patient") {
                    findNavController().navigate(R.id.action_splash_to_homeActivity)
                }else{
                    findNavController().navigate(R.id.action_splash_to_profileDoctorActivity)
                }
            }
            else if(onBoardingFinished()){
                findNavController().navigate(R.id.action_splash_to_loginActivity)
            }else{
                findNavController().navigate(R.id.action_splash_to_viewPagerFragment)
            }        },3500)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

    private fun checkLoggedIn() : Boolean{
        // check if user is logged in
        val prefs = requireActivity().getSharedPreferences(
            MyConfig.SHARED_PREFS_FILENAME,
            AppCompatActivity.MODE_PRIVATE
        )
        prefs.getString("token", null) ?: return false
        return true
    }

}