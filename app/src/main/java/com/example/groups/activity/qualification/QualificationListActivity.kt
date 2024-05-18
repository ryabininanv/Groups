package com.example.groups.activity.qualification
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.example.groups.fragment.group.GroupListFragment
import com.example.groups.fragment.SingleFragmentActivity
import com.example.groups.fragment.form_education.FormListFragment
import com.example.groups.fragment.qualification.QualificationListFragment

class QualificationListActivity: SingleFragmentActivity() {
    companion object{
        fun newIntent(packageContext: Context?): Intent?{
            val intent = Intent(packageContext, QualificationListActivity::class.java)
            return intent
        }
    }

    override fun createFragment(): Fragment = QualificationListFragment()
}