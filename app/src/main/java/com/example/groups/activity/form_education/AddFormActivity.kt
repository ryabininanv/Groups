package com.example.groups.activity.form_education

import android.content.Context
import android.content.Intent
import com.example.groups.fragment.SingleFragmentActivity
import com.example.groups.fragment.form_education.AddFormFragment
import com.example.groups.fragment.group.AddGroupFragment
import com.example.groups.fragment.group.GroupFragment

class AddFormActivity : SingleFragmentActivity() {
    companion object{

        fun newIntent(packageContext: Context?): Intent?{
            val intent = Intent(packageContext, AddFormActivity::class.java)
            return intent
        }
    }

    override fun createFragment(): AddFormFragment {
        return AddFormFragment()
    }
}