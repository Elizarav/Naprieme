package com.example.naprieme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.naprieme.activities.RegisterActivity
import com.example.naprieme.databinding.ActivityMainBinding
import com.example.naprieme.models.User
import com.example.naprieme.ui.fragments.ChatsFragment
import com.example.naprieme.ui.objects.AppDrawer
import com.example.naprieme.utilits.AUTH
import com.example.naprieme.utilits.AppValueEventListener
import com.example.naprieme.utilits.NODE_USERS
import com.example.naprieme.utilits.REF_DATABASE_ROOT
import com.example.naprieme.utilits.UID
import com.example.naprieme.utilits.USER
import com.example.naprieme.utilits.initFirebase
import com.example.naprieme.utilits.replaceActivity
import com.example.naprieme.utilits.replaceFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

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
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
            .addListenerForSingleValueEvent(AppValueEventListener {
                USER = it.getValue(User::class.java) ?: User()
            })
    }

}
