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
import com.example.groups.activity.speciality.SpecialityListActivity
import com.example.groups.models.UrlList
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AddSpecialityFragment: Fragment() {


    private val client = OkHttpClient()
    private lateinit var name: EditText
    private lateinit var profile: EditText
    private lateinit var backButton: ImageButton
    private lateinit var saveButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.speciality_add_fragment, container, false)

        name = v.findViewById(R.id.name)
        profile = v.findViewById(R.id.profile)
        backButton =  v.findViewById(R.id.back)
        saveButton = v.findViewById(R.id.save)


        backButton.setOnClickListener{ goBack()}


        saveButton.setOnClickListener {
            if (name.text.isEmpty() && profile.text.isEmpty()){
                Toast.makeText(context, "Пропущенные некоторые поля", Toast.LENGTH_LONG).show()
            }
            else {
                val body = MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("name_speciality", name.text.toString())
                    .addFormDataPart("profile", profile.text.toString())
                    .build()

                val request = Request.Builder().url(UrlList.speciality).post(body).build()


                client.newCall(request).enqueue(object : Callback{

                    override fun onFailure(call: Call, e: IOException){
                        Log.e("SpecialityFragmentError", e.toString())
                    }

                    override fun onResponse(call: Call, response: Response) {
                        goBack()
                    }
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