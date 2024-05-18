package com.example.groups.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.groups.R
import com.example.groups.models.Qualification

class QualificationAdapter(context: Context, list: List<Qualification>) : ArrayAdapter<Qualification>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {

        val speciality = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.form_spinner_item, parent, false)
        if (speciality != null) {
            val name = view.findViewById<TextView>(R.id.form_name)

            name.text = speciality.name
        }

        return view
    }
}