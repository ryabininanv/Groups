package com.example.groups.activity.authorization

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.groups.activity.group.GroupListActivity
import com.example.groups.fragment.authorization.AuthorizationFragment
import com.example.groups.fragment.SingleFragmentActivity

class AuthorizationActivity: SingleFragmentActivity() {
    companion object{
        fun newIntent(packageContext: Context?): Intent?{
            val intent = Intent(packageContext, GroupListActivity::class.java)
            return intent
        }
    }

    override fun createFragment(): Fragment = AuthorizationFragment()
}