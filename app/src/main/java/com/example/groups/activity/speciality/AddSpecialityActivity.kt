package com.example.groups.activity.speciality

import android.content.Context
import android.content.Intent
import com.example.groups.fragment.SingleFragmentActivity
import com.example.groups.fragment.form_education.AddFormFragment
import com.example.groups.fragment.group.AddGroupFragment
import com.example.groups.fragment.group.GroupFragment
import com.example.groups.fragment.speciality.AddSpecialityFragment

class AddSpecialityActivity : SingleFragmentActivity() {
    companion object{

        fun newIntent(packageContext: Context?): Intent{
            val intent = Intent(packageContext, AddSpecialityActivity::class.java)
            return intent
        }
    }

    override fun createFragment(): AddSpecialityFragment {
        return AddSpecialityFragment()
    }
}