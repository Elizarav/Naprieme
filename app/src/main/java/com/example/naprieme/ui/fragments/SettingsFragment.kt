package com.example.naprieme.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.naprieme.R
import com.example.naprieme.activities.RegisterActivity
import com.example.naprieme.databinding.FragmentSettingsBinding
import com.example.naprieme.utilits.APP_ACTIVITY
import com.example.naprieme.utilits.AUTH
import com.example.naprieme.utilits.FOLDER_PROFILE_IMAGE
import com.example.naprieme.utilits.REF_STORAGE_ROOT
import com.example.naprieme.utilits.CURRENT_UID
import com.example.naprieme.utilits.USER
import com.example.naprieme.utilits.replaceActivity
import com.example.naprieme.utilits.replaceFragment
import com.example.naprieme.utilits.showToast
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

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
        mBinding.settingsChangePhoto.setOnClickListener {
            changePhotoUser()
        }
    }

    private fun changePhotoUser() {
        CropImage.activity()
            .setAspectRatio(1, 1)
            .setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL)
            .start(APP_ACTIVITY)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.settings_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings_menu_exit -> {
                AUTH.signOut()
                (APP_ACTIVITY).replaceActivity(RegisterActivity())
            }

            R.id.settings_menu_change_name -> replaceFragment(ChangeNameFragment())
        }
        return true
    }
}