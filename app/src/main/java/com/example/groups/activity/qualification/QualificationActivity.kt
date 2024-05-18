package com.example.groups.activity.qualification

import android.content.Context
import android.content.Intent
import com.example.groups.fragment.group.GroupFragment
import com.example.groups.fragment.SingleFragmentActivity
import com.example.groups.fragment.form_education.FormFragment
import com.example.groups.fragment.qualification.QualificationFragment

class QualificationActivity : SingleFragmentActivity() {
    companion object{
        const val EXTRA_ID = "com.example.qualification.id"

        fun newIntent(packageContext: Context?, id: Int?): Intent {
            val intent = Intent(packageContext, QualificationActivity::class.java)
            intent.putExtra(EXTRA_ID,id)
            return intent
        }
    }

    override fun createFragment(): QualificationFragment {
        val form_id = intent.getSerializableExtra(EXTRA_ID) as Int?
        return QualificationFragment.newInstance(form_id)
    }
}