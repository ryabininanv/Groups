package com.example.groups.activity.form_education
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.groups.fragment.group.GroupListFragment
import com.example.groups.fragment.SingleFragmentActivity
import com.example.groups.fragment.form_education.FormListFragment

class FormListActivity: SingleFragmentActivity() {
    companion object{
        fun newIntent(packageContext: Context?): Intent?{
            val intent = Intent(packageContext, FormListActivity::class.java)
            return intent
        }
    }

    override fun createFragment(): Fragment = FormListFragment()
}