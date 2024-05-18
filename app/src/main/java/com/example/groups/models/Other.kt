package com.example.groups.models

import android.content.Context
import android.util.Log
import com.example.groups.models.Group
import android.widget.Button
import android.widget.ImageButton
import com.example.groups.R
import com.example.groups.activity.form_education.FormListActivity
import com.example.groups.activity.group.GroupListActivity
import com.example.groups.activity.qualification.QualificationListActivity
import com.example.groups.activity.speciality.SpecialityListActivity
import org.json.JSONObject

class Other {
    companion object{
        fun popMenu(context: Context?, menuButton: ImageButton){
            val popupMenu = android.widget.PopupMenu(context, menuButton)
            popupMenu.menuInflater.inflate(R.menu.pop_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.group_menuitem -> {context?.startActivity(GroupListActivity.newIntent(context))}
                    R.id.form_menuitem -> {context?.startActivity(FormListActivity.newIntent(context))}
                    R.id.speciality_menuitem -> {context?.startActivity(SpecialityListActivity.newIntent(context)) }
                    R.id.qualification_menuitem -> {context?.startActivity(QualificationListActivity.newIntent(context))}
                }
                true
            }
            popupMenu.show()
        }

        fun getGroupFromJSON(jsonObject: JSONObject): Group{
            return Group(
                id = jsonObject.getInt("id"),
                name = jsonObject.getString("name_group"),
                faculty = jsonObject.getString("faculty"),
                course = jsonObject.getInt("course"),
                count_student = jsonObject.getInt("count_student"),
                count_subgroup = jsonObject.getInt("count_subgroup"),
                form_name = jsonObject.getString("name_from"),
                speciality_name = jsonObject.getString("name_speciality"),
                qualification_name = jsonObject.getString("name_qualification"),
                form_id = jsonObject.getInt("id_form_education"),
                speciality_id = jsonObject.getInt("id_speciality"),
                qualification_id = jsonObject.getInt("id_qualification")
            )
        }

        fun getFormEducationFromJSON(jsonObject: JSONObject): FormEducation{
            return FormEducation(
                id = jsonObject.getInt("id"),
                formName = jsonObject.getString("form_education")
            )
        }

        fun getSpecialityFromJSON(jsonObject: JSONObject): Speciality{
            return Speciality(
                id = jsonObject.getInt("id"),
                name = jsonObject.getString("name_speciality"),
                profile = jsonObject.getString("profile")
            )
        }

        fun getQualificationFromJSON(jsonObject: JSONObject): Qualification{
            return Qualification(
                id = jsonObject.getInt("id"),
                name = jsonObject.getString("name_qualification")
            )
        }
    }
}