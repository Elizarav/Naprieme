package com.example.naprieme.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.naprieme.R
import com.example.naprieme.databinding.FragmentChangeBioBinding
import com.example.naprieme.databinding.FragmentChangeUsernameBinding
import com.example.naprieme.utilits.CHILD_BIO
import com.example.naprieme.utilits.NODE_USERS
import com.example.naprieme.utilits.REF_DATABASE_ROOT
import com.example.naprieme.utilits.UID
import com.example.naprieme.utilits.USER
import com.example.naprieme.utilits.showToast


class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    private var _binding: FragmentChangeBioBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeBioBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        mBinding.settingInputBio.setText(USER.bio)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun change() {
        super.change()
        val newBio =  mBinding.settingInputBio.text.toString()
            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_BIO).setValue(newBio)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_update))
                        USER.bio = newBio
                        fragmentManager?.popBackStack()
                    }
                }
    }
}