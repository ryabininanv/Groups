package com.example.groups.fragment.qualification

import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.groups.models.Qualification
import okhttp3.MultipartBody
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.groups.R
import com.example.groups.activity.qualification.QualificationActivity.Companion.EXTRA_ID
import com.example.groups.activity.qualification.QualificationListActivity
import com.example.groups.models.Other
import com.example.groups.models.UrlList
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class QualificationFragment: Fragment() {

    companion object{
        fun newInstance(id: Int?) =
            QualificationFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(EXTRA_ID, id)
                }
            }
    }

    private val client = OkHttpClient()
    private var qualification: Qualification? = null
    private var id = -1
    private lateinit var name: EditText
    private lateinit var backButton: ImageButton
    private lateinit var deleteButton: ImageButton
    private lateinit var saveButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = (requireActivity().intent.getSerializableExtra(EXTRA_ID) as Int?)!!
        val request = Request.Builder().url("${UrlList.qualification}/${id}").build()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException){
                Log.e("QualificationFragmentError", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                qualification = Other.getQualificationFromJSON(JSONObject(response.body()!!.string()))
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.qualification_fragment, container, false)

        name = v.findViewById(R.id.name)
        backButton =  v.findViewById(R.id.back)
        deleteButton = v.findViewById(R.id.delete)
        saveButton = v.findViewById(R.id.save)


        name.setText(qualification?.name)

        backButton.setOnClickListener{ goBack()}

        deleteButton.setOnClickListener{
            client.newCall(Request.Builder().url("${UrlList.qualification}/${qualification?.id}").delete().build()).enqueue(object : Callback{

                override fun onFailure(call: Call, e: IOException){
                    Log.e("QualificationFragmentError", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {goBack()}
            })
        }

        saveButton.setOnClickListener {
            if (name.text.isEmpty()){
                Toast.makeText(context, "Пропущенные некоторые поля", Toast.LENGTH_LONG).show()
            }
            else {
                val body = MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("name_qualification", name.text.toString())
                    .build()

                val request = Request.Builder().url("${UrlList.qualification}/${qualification?.id}").put(body).build()


                client.newCall(request).enqueue(object : Callback{

                    override fun onFailure(call: Call, e: IOException){
                        Log.e("QualificationFragmentError", e.toString())
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
        val intent = QualificationListActivity.newIntent(context)
        context?.startActivity(intent)
    }

}