package com.example.groups.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.groups.R
import com.example.groups.models.FormEducation

class FormAdapter(context: Context, list: List<FormEducation>) : ArrayAdapter<FormEducation>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {

        val formEducation = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.form_spinner_item, parent, false)
        if (formEducation != null) {
            val form_name = view.findViewById<TextView>(R.id.form_name)

            form_name.text = formEducation.formName
        }

        return view
    }
}