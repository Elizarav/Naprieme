package com.example.naprieme.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naprieme.MainActivity
import com.example.naprieme.R
import com.example.naprieme.utilits.APP_ACTIVITY


open class BaseFragment(private val layout: Int) : Fragment(layout) {


     override fun onStart() {
         super.onStart()
         (APP_ACTIVITY).mAppDrawer.disableDrawer()
     }

     override fun onStop() {
         super.onStop()
         (APP_ACTIVITY).mAppDrawer.enableDrawer()
     }
 }