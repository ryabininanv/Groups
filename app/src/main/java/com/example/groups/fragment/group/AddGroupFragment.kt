package com.example.groups.fragment.group

import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.example.groups.activity.group.GroupListActivity
import com.example.groups.adapters.QualificationAdapter
import com.example.groups.adapters.SpecialityAdapter
import com.example.groups.models.Qualification
import com.example.groups.models.Speciality
import okhttp3.MultipartBody
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.groups.R
import com.example.groups.models.FormEducation
import com.example.groups.models.Other
import com.example.groups.models.UrlList
import com.example.groups.adapters.FormAdapter
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class AddGroupFragment: Fragment() {

    private val client = OkHttpClient()
    private lateinit var name: EditText
    private var forms = mutableListOf<FormEducation>()
    private var specialityList = mutableListOf<Speciality>()
    private var qualificationList = mutableListOf<Qualification>()
    private lateinit var faculty: EditText
    private lateinit var course: EditText
    private var form_id = -1
    private var speciality_id = -1
    private var qualification_id = -1
    private lateinit var count_student: EditText
    private lateinit var count_subgroup: EditText
    private lateinit var form_spinner: Spinner
    private lateinit var speciality_spinner: Spinner
    private lateinit var qualification_spinner: Spinner
    private lateinit var backButton: ImageButton
    private lateinit var saveButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        client.newCall(Request.Builder().url(UrlList.forms).build()).enqueue(object : Callback{

            override fun onFailure(call: Call, e: IOException){
                Log.e("GroupFragmentError", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val result = JSONObject(response.body()!!.string()).getJSONArray("items")
                forms.clear()
                for(i in 0..<result.length()){
                    forms.add(Other.getFormEducationFromJSON(JSONObject(result.get(i).toString())))
                }
            }
        })
        client.newCall(Request.Builder().url(UrlList.speciality).build()).enqueue(object : Callback{

            override fun onFailure(call: Call, e: IOException){
                Log.e("GroupFragmentError", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val result = JSONObject(response.body()!!.string()).getJSONArray("items")
                specialityList.clear()
                for(i in 0..<result.length()){
                    specialityList.add(Other.getSpecialityFromJSON(JSONObject(result.get(i).toString())))
                }
            }
        })
        client.newCall(Request.Builder().url(UrlList.qualification).build()).enqueue(object : Callback{

            override fun onFailure(call: Call, e: IOException){
                Log.e("GroupFragmentError", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val result = JSONObject(response.body()!!.string()).getJSONArray("items")
                qualificationList.clear()
                for(i in 0..<result.length()){
                    qualificationList.add(Other.getQualificationFromJSON(JSONObject(result.get(i).toString())))
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.group_add_fragment, container, false)

        form_spinner = v.findViewById(R.id.form_spinner)
        speciality_spinner = v.findViewById(R.id.speciality_spinner)
        qualification_spinner = v.findViewById(R.id.qualification_spinner)
        name = v.findViewById(R.id.name)
        faculty = v.findViewById(R.id.faculty)
        count_student = v.findViewById(R.id.count_student)
        count_subgroup = v.findViewById(R.id.count_subgroup)
        course = v.findViewById(R.id.course)
        backButton =  v.findViewById(R.id.back)
        saveButton = v.findViewById(R.id.save)



        form_spinner.adapter = FormAdapter(requireContext(), forms)
        form_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               form_id=forms.get(position).id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        speciality_spinner.adapter = SpecialityAdapter(requireContext(), specialityList)
        speciality_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               speciality_id=specialityList.get(position).id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        qualification_spinner.adapter = QualificationAdapter(requireContext(), qualificationList)
        qualification_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               qualification_id=qualificationList.get(position).id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        backButton.setOnClickListener{ goBack()}

        saveButton.setOnClickListener {
            if (name.text.isEmpty() && faculty.text.isEmpty() && count_student.text.isEmpty() && count_subgroup.text.isEmpty() && course.text.isEmpty()){
                Toast.makeText(context, "Пропущенные некоторые поля", Toast.LENGTH_LONG).show()
            }
            else {
                val body = MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("name_group", name.text.toString())
                    .addFormDataPart("faculty", faculty.text.toString())
                    .addFormDataPart("course", course.text.toString())
                    .addFormDataPart("count_student", count_student.text.toString())
                    .addFormDataPart("count_subgroup", count_subgroup.text.toString())
                    .addFormDataPart("id_speciality", speciality_id.toString())
                    .addFormDataPart("id_qualification", qualification_id.toString())
                    .addFormDataPart("id_form_education", form_id.toString())
                    .build()

                val request = Request.Builder().url(UrlList.group).post(body).build()


                client.newCall(request).enqueue(object : Callback{

                    override fun onFailure(call: Call, e: IOException){
                        Log.e("GroupFragmentError", e.toString())
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
        val intent = GroupListActivity.newIntent(context)
        context?.startActivity(intent)
    }

}