package com.example.groups.activity.group
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.groups.fragment.group.GroupListFragment
import com.example.groups.fragment.SingleFragmentActivity

class GroupListActivity: SingleFragmentActivity() {
    companion object{
        fun newIntent(packageContext: Context?): Intent?{
            val intent = Intent(packageContext, GroupListActivity::class.java)
            return intent
        }
    }

    override fun createFragment(): Fragment = GroupListFragment()
}