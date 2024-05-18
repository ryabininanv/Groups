package com.example.groups.activity.group

import android.content.Context
import android.content.Intent
import com.example.groups.fragment.group.GroupFragment
import com.example.groups.fragment.group.GroupListFragment
import com.example.groups.fragment.SingleFragmentActivity
import java.util.UUID

class GroupActivity : SingleFragmentActivity() {
    companion object{
        const val EXTRA_ID = "com.example.groups.id"

        fun newIntent(packageContext: Context?, id: Int?): Intent?{
            val intent = Intent(packageContext, GroupActivity::class.java)
            intent.putExtra(EXTRA_ID,id)
            return intent
        }
    }

    override fun createFragment(): GroupFragment {
        val group_id = intent.getSerializableExtra(EXTRA_ID) as Int?
        return GroupFragment.newInstance(group_id)
    }
}