package com.example.naprieme

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.naprieme.activities.RegisterActivity
import com.example.naprieme.databinding.ActivityMainBinding
import com.example.naprieme.models.User
import com.example.naprieme.ui.fragments.ChatsFragment
import com.example.naprieme.ui.objects.AppDrawer
import com.example.naprieme.utilits.APP_ACTIVITY
import com.example.naprieme.utilits.AUTH
import com.example.naprieme.utilits.AppValueEventListener
import com.example.naprieme.utilits.NODE_USERS
import com.example.naprieme.utilits.REF_DATABASE_ROOT
import com.example.naprieme.utilits.CURRENT_UID
import com.example.naprieme.utilits.FOLDER_PROFILE_IMAGE
import com.example.naprieme.utilits.REF_STORAGE_ROOT
import com.example.naprieme.utilits.USER
import com.example.naprieme.utilits.initFirebase
import com.example.naprieme.utilits.replaceActivity
import com.example.naprieme.utilits.replaceFragment
import com.example.naprieme.utilits.showToast
import com.theartofdev.edmodo.cropper.CropImage

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mToolbar: Toolbar
    lateinit var mAppDrawer: AppDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        APP_ACTIVITY = this
        initFields()
        initFunc()
    }

    private fun initFunc() {
        if (AUTH.currentUser != null) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(ChatsFragment(), false)
        } else {
            replaceActivity(RegisterActivity())
        }

    }

    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)
        initFirebase()
        initUser()
    }

    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID)
            .addListenerForSingleValueEvent(AppValueEventListener {
                USER = it.getValue(User::class.java) ?: User()
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == Activity.RESULT_OK && data != null){
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                .child(CURRENT_UID)
            path.putFile(uri).addOnCompleteListener {
                if (it.isSuccessful){
                    showToast(getString(R.string.toast_data_update))
                }
            }
        }
    }

}
