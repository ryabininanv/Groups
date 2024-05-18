package com.example.groups.fragment.qualification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.groups.R
import com.example.groups.activity.qualification.AddQualificationActivity
import com.example.groups.activity.qualification.QualificationActivity
import com.example.groups.models.Other
import com.example.groups.models.Qualification
import com.example.groups.models.UrlList
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class QualificationListFragment: Fragment() {
    private val client = OkHttpClient()
    private lateinit var addButton: ImageButton
    private lateinit var menuButton: ImageButton
    private var recycleView: RecyclerView? = null
    private var adapter: QualificationAdapter? = null
    private var qualificationList = mutableListOf<Qualification>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.qualification_list_fragment, container, false)
        recycleView = view.findViewById(R.id.list_recycler_view)
        addButton = view.findViewById(R.id.add)
        menuButton = view.findViewById(R.id.menu)

        addButton.setOnClickListener {
            val intent = AddQualificationActivity.newIntent(context)
            context?.startActivity(intent)
        }
        menuButton.setOnClickListener {Other.popMenu(context,menuButton)}
        recycleView!!.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onResume() {super.onResume(); updateUI()}

    private fun updateUI(){
        val request = Request.Builder().url(UrlList.qualification).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("QualificationListFragmentError", e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                val result = JSONObject(response.body()!!.string()).getJSONArray("items")
                qualificationList.clear()
                for(i in 0..<result.length()){
                    qualificationList.add(Other.getQualificationFromJSON(JSONObject(result.get(i).toString())))
                }
            }
        })

        if (adapter == null){
            adapter = QualificationAdapter(qualificationList)
            recycleView!!.adapter = adapter
        }
    }

    private class Holder (item: View?): ViewHolder(item!!), View.OnClickListener {
        var nameTextView: TextView? = itemView.findViewById(R.id.list_item_name)


        private lateinit var qualification: Qualification

        fun bind(qualification: Qualification){
            this.qualification = qualification
            nameTextView?.text = qualification.name
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = v!!.context
            val intent = QualificationActivity.newIntent(context, qualification.id)
            context.startActivity(intent)
        }
    }
    private class QualificationAdapter(qualifications: List<Qualification>?): RecyclerView.Adapter<Holder?>(){
        private var qualificationList: List<Qualification>? = null
        init { this.qualificationList = qualifications }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.qualification_list_item, parent,false)
            return Holder(view)
        }

        override fun getItemCount(): Int { return  qualificationList!!.size}

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val qualification = qualificationList!![position]
            holder.bind(qualification)
        }
    }


}