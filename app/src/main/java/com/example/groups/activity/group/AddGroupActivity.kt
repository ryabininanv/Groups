package com.example.groups.activity.group

import android.content.Context
import android.content.Intent
import com.example.groups.fragment.SingleFragmentActivity
import com.example.groups.fragment.group.AddGroupFragment
import com.example.groups.fragment.group.GroupFragment

class AddGroupActivity : SingleFragmentActivity() {
    companion object{

        fun newIntent(packageContext: Context?): Intent?{
            val intent = Intent(packageContext, AddGroupActivity::class.java)
            return intent
        }
    }

    override fun createFragment(): AddGroupFragment {
        return AddGroupFragment()
    }
}