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
import com.example.naprieme.activities.RegisterActivity
import com.example.naprieme.databinding.FragmentSettingsBinding
import com.example.naprieme.utilits.AUTH
import com.example.naprieme.utilits.USER
import com.example.naprieme.utilits.replaceActivity
import com.example.naprieme.utilits.replaceFragment

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        mBinding.settingsBio.text = USER.bio
        mBinding.settingsFullName.text = USER.fullname
        mBinding.settingsPhoneNumber.text = USER.phone
        mBinding.settingsStatus.text = USER.status
        mBinding.settingsUsername.text = USER.username
        mBinding.settingsBtnChangeUsername.setOnClickListener {
            replaceFragment(
                ChangeUsernameFragment()
            )
        }
        mBinding.settingsBtnChangeBio.setOnClickListener {
            replaceFragment(
                ChangeBioFragment()
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }

            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }
}