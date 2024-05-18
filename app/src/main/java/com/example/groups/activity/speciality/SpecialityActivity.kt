package com.example.groups.activity.speciality

import android.content.Context
import android.content.Intent
import com.example.groups.fragment.SingleFragmentActivity
import com.example.groups.fragment.speciality.SpecialityFragment

class SpecialityActivity : SingleFragmentActivity() {
    companion object{
        const val EXTRA_ID = "com.example.speciality.id"

        fun newIntent(packageContext: Context?, id: Int?): Intent{
            val intent = Intent(packageContext, SpecialityActivity::class.java)
            intent.putExtra(EXTRA_ID,id)
            return intent
        }
    }

    override fun createFragment(): SpecialityFragment {
        val id = intent.getSerializableExtra(EXTRA_ID) as Int?
        return SpecialityFragment.newInstance(id)
    }
}