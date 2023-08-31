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
import com.example.naprieme.databinding.FragmentChangeUsernameBinding
import com.example.naprieme.utilits.AppValueEventListener
import com.example.naprieme.utilits.CHILD_USERNAME
import com.example.naprieme.utilits.NODE_USERNAMES
import com.example.naprieme.utilits.NODE_USERS
import com.example.naprieme.utilits.REF_DATABASE_ROOT
import com.example.naprieme.utilits.UID
import com.example.naprieme.utilits.USER
import com.example.naprieme.utilits.showToast
import java.util.Locale
import kotlin.concurrent.fixedRateTimer


class ChangeUsernameFragment : BaseChangeFragment(R.layout.fragment_change_username) {

    private var _binding: FragmentChangeUsernameBinding? = null
    private val mBinding get() = _binding!!
    lateinit var mNewUsername: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeUsernameBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        mBinding.settingInputUsername.setText(USER.username)
    }

    override fun change() {
        mNewUsername =
            mBinding.settingInputUsername.text.toString().toLowerCase(Locale.getDefault())
        if (mNewUsername.isEmpty()) {
            showToast("Поле пустое")
        } else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if (it.hasChild(mNewUsername)) {
                        showToast("Такой пользователь уже существует")
                    } else {
                        changeUsername()
                    }
                })
        }
    }

    private fun changeUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(UID)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    updateCurrentUsername()
                }
            }

    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_USERNAME)
            .setValue(mNewUsername)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    deleteOldUsername()
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }

    private fun deleteOldUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    fragmentManager?.popBackStack()
                    USER.username = mNewUsername
                } else {
                    showToast(it.exception?.message.toString())
                }
            }
    }
}