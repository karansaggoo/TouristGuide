package com.example.touristguide.model

import java.util.*

data class User(var id : String = UUID.randomUUID().toString(),
                var email : String = "",
                var name : String = "",
                var password : String = "") {
}