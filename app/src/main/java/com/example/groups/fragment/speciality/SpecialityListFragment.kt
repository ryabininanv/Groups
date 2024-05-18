package com.example.groups.fragment.speciality

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.groups.R
import com.example.groups.activity.speciality.AddSpecialityActivity
import com.example.groups.activity.speciality.SpecialityActivity
import com.example.groups.models.Other
import com.example.groups.models.Speciality
import com.example.groups.models.UrlList
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class SpecialityListFragment: Fragment() {
    private val client = OkHttpClient()
    private lateinit var addButton: ImageButton
    private lateinit var menuButton: ImageButton
    private var recycleView: RecyclerView? = null
    private var adapter: SpecialityAdapter? = null
    private var specialities = mutableListOf<Speciality>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.speciality_list_fragment, container, false)
        recycleView = view.findViewById(R.id.list_recycler_view)
        addButton = view.findViewById(R.id.add)
        menuButton = view.findViewById(R.id.menu)
        addButton.setOnClickListener {
            val intent = AddSpecialityActivity.newIntent(context)
            context?.startActivity(intent)
        }
        menuButton.setOnClickListener {Other.popMenu(context,menuButton)}
        recycleView!!.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onResume() {super.onResume(); updateUI()}

    private fun updateUI(){
        val request = Request.Builder().url(UrlList.speciality).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("SpecialityListFragmentError", e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                val result = JSONObject(response.body()!!.string()).getJSONArray("items")
                specialities.clear()
                for(i in 0..<result.length()){
                    specialities.add(Other.getSpecialityFromJSON(JSONObject(result.get(i).toString())))
                }
            }
        })
        if (adapter == null){
            adapter = SpecialityAdapter(specialities)
            recycleView!!.adapter = adapter
        }
    }

    private class Holder (item: View?): ViewHolder(item!!), View.OnClickListener {
        var nameTextView: TextView? = itemView.findViewById(R.id.list_item_name)


        private lateinit var speciality: Speciality

        fun bindForm(speciality1: Speciality){
            this.speciality = speciality1
            nameTextView?.text = speciality1.name
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = v!!.context
            val intent = SpecialityActivity.newIntent(context, speciality.id)
            context.startActivity(intent)
        }
    }
    private class SpecialityAdapter(specialityList: List<Speciality>?): RecyclerView.Adapter<Holder?>(){
        private var specialities: List<Speciality>? = null
        init { this.specialities = specialityList }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.speciality_list_item, parent,false)
            return Holder(view)
        }

        override fun getItemCount(): Int { return  specialities!!.size}

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val speciality = specialities!![position]
            holder.bindForm(speciality)
        }
    }


}