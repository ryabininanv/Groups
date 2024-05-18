package com.example.groups.fragment.speciality

import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import okhttp3.MultipartBody
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.groups.R
import com.example.groups.activity.form_education.FormListActivity
import com.example.groups.activity.speciality.SpecialityActivity.Companion.EXTRA_ID
import com.example.groups.activity.speciality.SpecialityListActivity
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

class SpecialityFragment: Fragment() {

    companion object{
        fun newInstance(id: Int?) =
            SpecialityFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(EXTRA_ID, id)
                }
            }
    }

    private val client = OkHttpClient()
    private var speciality: Speciality? = null
    private var id = -1
    private lateinit var name: EditText
    private lateinit var profile: EditText
    private lateinit var backButton: ImageButton
    private lateinit var deleteButton: ImageButton
    private lateinit var saveButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = (requireActivity().intent.getSerializableExtra(EXTRA_ID) as Int?)!!
        val request = Request.Builder().url("${UrlList.speciality}/${id}").build()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException){
                Log.e("SpecialityFragmentError", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                speciality = Other.getSpecialityFromJSON(JSONObject(response.body()!!.string()))
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.speciality_fragment, container, false)

        name = v.findViewById(R.id.name)
        profile = v.findViewById(R.id.profile)
        backButton =  v.findViewById(R.id.back)
        deleteButton = v.findViewById(R.id.delete)
        saveButton = v.findViewById(R.id.save)


        name.setText(speciality?.name)
        profile.setText(speciality?.profile)

        backButton.setOnClickListener{ goBack()}

        deleteButton.setOnClickListener{
            client.newCall(Request.Builder().url("${UrlList.speciality}/${speciality?.id}").delete().build()).enqueue(object : Callback{

                override fun onFailure(call: Call, e: IOException){
                    Log.e("SpecialityFragmentError", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {goBack()}
            })
        }

        saveButton.setOnClickListener {
            if (name.text.isEmpty() && profile.text.isEmpty()){
                Toast.makeText(context, "Пропущенные некоторые поля", Toast.LENGTH_LONG).show()
            }
            else {
                val body = MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("name_speciality", name.text.toString())
                    .addFormDataPart("profile", profile.text.toString())
                    .build()

                val request = Request.Builder().url("${UrlList.speciality}/${speciality?.id}").put(body).build()


                client.newCall(request).enqueue(object : Callback{

                    override fun onFailure(call: Call, e: IOException){
                        Log.e("SpecialityFragmentError", e.toString())
                    }

                    override fun onResponse(call: Call, response: Response) { goBack()}
                })
            }
        }

        return v
    }

    fun goBack(){
        val intent = SpecialityListActivity.newIntent(context)
        context?.startActivity(intent)
    }

}