package com.example.groups.models

import java.util.Date
import java.util.UUID

class Group {
    var id: Int
    var name: String
    var faculty: String
    var course: Int
    var count_student: Int
    var count_subgroup: Int
    var form_name: String
    var speciality_name: String
    var qualification_name: String
    var form_id: Int
    var speciality_id: Int
    var qualification_id: Int

    constructor(
        id: Int,
        name: String,
        faculty: String,
        course: Int,
        count_student: Int,
        count_subgroup: Int,
        form_name: String,
        speciality_name: String,
        qualification_name: String,
        form_id: Int,
        speciality_id: Int,
        qualification_id: Int
    ) {
        this.id = id
        this.name = name
        this.faculty = faculty
        this.course = course
        this.count_student = count_student
        this.count_subgroup = count_subgroup
        this.form_name = form_name
        this.speciality_name = speciality_name
        this.qualification_name = qualification_name
        this.form_id = form_id
        this.speciality_id = speciality_id
        this.qualification_id = qualification_id
    }
}