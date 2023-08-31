package com.example.naprieme.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.naprieme.MainActivity
import com.example.naprieme.R
import com.example.naprieme.databinding.FragmentChangeNameBinding
import com.example.naprieme.utilits.CHILD_FULLNAME
import com.example.naprieme.utilits.NODE_USERS
import com.example.naprieme.utilits.REF_DATABASE_ROOT
import com.example.naprieme.utilits.UID
import com.example.naprieme.utilits.USER
import com.example.naprieme.utilits.showToast
import kotlin.concurrent.fixedRateTimer


class ChangeNameFragment : BaseChangeFragment(R.layout.fragment_change_name) {

    private var _binding: FragmentChangeNameBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChangeNameBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        initFullnameList()
    }

    private fun initFullnameList() {
        val fullNameList = USER.fullname.split(" ")
        if (fullNameList.size > 1) {
            mBinding.settingInputName.setText(fullNameList[0])
            mBinding.settingInputSurname.setText(fullNameList[1])
        } else mBinding.settingInputName.setText(fullNameList[0])
    }

    override fun change() {
        val name = mBinding.settingInputName.text.toString()
        val surname = mBinding.settingInputSurname.text.toString()
        if (name.isEmpty()) {
            showToast(getString(R.string.settings_toast_name_isempty))
        } else {
            val fullname = "$name $surname"
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_FULLNAME)
                .setValue(fullname).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_update))
                        USER.fullname = fullname
                        fragmentManager?.popBackStack()
                    }
                }
        }
    }
}


