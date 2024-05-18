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
import com.example.groups.activity.group.GroupActivity.Companion.EXTRA_ID
import com.example.groups.models.Group
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

class GroupFragment: Fragment() {

    companion object{
        fun newInstance(id: Int?) =
            GroupFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(EXTRA_ID, id)
                }
            }
    }

    private val client = OkHttpClient()
    private var group: Group? = null
    private var id = -1
    private lateinit var name: EditText
    private var forms = mutableListOf<FormEducation>()
    private var specialityList = mutableListOf<Speciality>()
    private var qualificationList = mutableListOf<Qualification>()
    private lateinit var faculty: EditText
    private lateinit var course: EditText
    private lateinit var count_student: EditText
    private lateinit var count_subgroup: EditText
    private lateinit var form_spinner: Spinner
    private lateinit var speciality_spinner: Spinner
    private lateinit var qualification_spinner: Spinner
    private lateinit var backButton: ImageButton
    private lateinit var deleteButton: ImageButton
    private lateinit var saveButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        id = (requireActivity().intent.getSerializableExtra(EXTRA_ID) as Int?)!!
        val request = Request.Builder().url("${UrlList.group}/${id}").build()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException){
                Log.e("GroupFragmentError", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                group = Other.getGroupFromJSON(JSONObject(response.body()!!.string()))
            }
        })
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
        val v = inflater.inflate(R.layout.group_fragment, container, false)

        form_spinner = v.findViewById(R.id.form_spinner)
        speciality_spinner = v.findViewById(R.id.speciality_spinner)
        qualification_spinner = v.findViewById(R.id.qualification_spinner)
        name = v.findViewById(R.id.name)
        faculty = v.findViewById(R.id.faculty)
        count_student = v.findViewById(R.id.count_student)
        count_subgroup = v.findViewById(R.id.count_subgroup)
        course = v.findViewById(R.id.course)
        backButton =  v.findViewById(R.id.back)
        deleteButton = v.findViewById(R.id.delete)
        saveButton = v.findViewById(R.id.save)



        form_spinner.adapter = FormAdapter(requireContext(), forms)
        form_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                group?.form_id=forms.get(position).id
                group?.form_name=forms.get(position).formName
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        speciality_spinner.adapter = SpecialityAdapter(requireContext(), specialityList)
        speciality_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                group?.speciality_id=specialityList.get(position).id
                group?.speciality_name=specialityList.get(position).name
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        qualification_spinner.adapter = QualificationAdapter(requireContext(), qualificationList)
        qualification_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                group?.qualification_id=qualificationList.get(position).id
                group?.qualification_name=qualificationList.get(position).name
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        name.setText(group?.name)
        faculty.setText(group?.faculty)
        count_student.setText(group?.count_student.toString())
        count_subgroup.setText(group?.count_subgroup.toString())
        course.setText(group?.course.toString())
        form_spinner.setSelection(forms.indexOfFirst {it.id == group?.form_id})
        speciality_spinner.setSelection(specialityList.indexOfFirst {it.id == group?.speciality_id})
        qualification_spinner.setSelection(qualificationList.indexOfFirst {it.id == group?.qualification_id})

        backButton.setOnClickListener{ goBack()}

        deleteButton.setOnClickListener{
            client.newCall(Request.Builder().url("${UrlList.group}/${group?.id}").delete().build()).enqueue(object : Callback{

                override fun onFailure(call: Call, e: IOException){
                    Log.e("GroupFragmentError", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {goBack()}
            })
        }

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
                    .addFormDataPart("id_speciality", group?.speciality_id.toString())
                    .addFormDataPart("id_qualification", group?.qualification_id.toString())
                    .addFormDataPart("id_form_education", group?.form_id.toString())
                    .build()

                val request = Request.Builder().url("${UrlList.group}/${group?.id}").put(body).build()


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