package com.example.groups.models

class UrlList {
    companion object{
        val base = "http://192.168.1.3:5000/"
        val authorization = "${base}/authorization"
        val group = "${base}/group"
        val forms = "${base}/forms"
        val speciality = "${base}/speciality"
        val qualification = "${base}/qualification"
    }
}