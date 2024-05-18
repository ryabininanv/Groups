package com.example.groups.fragment.group

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
import com.example.groups.activity.group.GroupActivity
import com.example.groups.models.Group
import com.example.groups.R
import com.example.groups.activity.group.AddGroupActivity
import com.example.groups.models.Other
import com.example.groups.models.UrlList
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class GroupListFragment: Fragment() {
    private val client = OkHttpClient()
    private lateinit var addButton: ImageButton
    private lateinit var menuButton: ImageButton
    private var groupRecycleView: RecyclerView? = null
    private var adapter: GroupAdapter? = null
    private var groups = mutableListOf<Group>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.group_list_fragment, container, false)
        groupRecycleView = view.findViewById(R.id.list_recycler_view)
        addButton = view.findViewById(R.id.add)
        menuButton = view.findViewById(R.id.menu)
        addButton.setOnClickListener {
            val intent = AddGroupActivity.newIntent(context)
            context?.startActivity(intent)
        }
        menuButton.setOnClickListener { Other.popMenu(context, menuButton) }
        groupRecycleView!!.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onResume() {super.onResume(); updateUI()}

    private fun updateUI(){
        val request = Request.Builder().url(UrlList.group).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("GroupListFragmentError", e.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                val result = JSONObject(response.body()!!.string()).getJSONArray("items")
                groups.clear()
                for(i in 0..<result.length()){
                    groups.add(Other.getGroupFromJSON(JSONObject(result.get(i).toString())))
                }
            }
        })

        if (adapter == null){
            adapter = GroupAdapter(groups)
            groupRecycleView!!.adapter = adapter
        }
    }

    private class Holder (item: View?): ViewHolder(item!!), View.OnClickListener {
        var nameTextView: TextView? = itemView.findViewById(R.id.list_item_name)


        private lateinit var group: Group

        fun bindGroup(group: Group){
            this.group = group
            nameTextView?.text = group.name
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val context = v!!.context
            val intent = GroupActivity.newIntent(context, group.id)
            context.startActivity(intent)
        }
    }
    private class GroupAdapter(groups: List<Group>?): RecyclerView.Adapter<Holder?>(){
        private var groups: List<Group>? = null
        init { this.groups = groups }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.group_list_item, parent,false)
            return Holder(view)
        }

        override fun getItemCount(): Int { return  groups!!.size}

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val group = groups!![position]
            holder.bindGroup(group)
        }
    }

}