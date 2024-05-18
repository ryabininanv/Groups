package com.example.groups.activity.speciality
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.groups.fragment.SingleFragmentActivity
import com.example.groups.fragment.speciality.SpecialityListFragment

class SpecialityListActivity: SingleFragmentActivity() {
    companion object{
        fun newIntent(packageContext: Context?): Intent{
            val intent = Intent(packageContext, SpecialityListActivity::class.java)
            return intent
        }
    }

    override fun createFragment(): Fragment = SpecialityListFragment()
}