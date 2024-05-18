package com.example.groups.activity.form_education

import android.content.Context
import android.content.Intent
import com.example.groups.fragment.group.GroupFragment
import com.example.groups.fragment.SingleFragmentActivity
import com.example.groups.fragment.form_education.FormFragment

class FormActivity : SingleFragmentActivity() {
    companion object{
        const val EXTRA_ID = "com.example.form.id"

        fun newIntent(packageContext: Context?, id: Int?): Intent?{
            val intent = Intent(packageContext, FormActivity::class.java)
            intent.putExtra(EXTRA_ID,id)
            return intent
        }
    }

    override fun createFragment(): FormFragment {
        val form_id = intent.getSerializableExtra(EXTRA_ID) as Int?
        return FormFragment.newInstance(form_id)
    }
}