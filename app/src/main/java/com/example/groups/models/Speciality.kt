package com.example.groups.models

class Speciality {
    var id: Int
    var name: String
    var profile: String

    constructor(
        id: Int,
        name: String,
        profile: String
    ) {
        this.id = id
        this.name = name
        this.profile = profile
    }
}