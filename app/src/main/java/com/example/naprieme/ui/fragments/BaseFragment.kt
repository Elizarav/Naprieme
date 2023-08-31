package com.example.naprieme.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naprieme.MainActivity
import com.example.naprieme.R


 open class BaseFragment(private val layout: Int) : Fragment(layout) {


     override fun onStart() {
         super.onStart()
         (activity as MainActivity).mAppDrawer.disableDrawer()
     }

     override fun onStop() {
         super.onStop()
         (activity as MainActivity).mAppDrawer.enableDrawer()
     }
 }