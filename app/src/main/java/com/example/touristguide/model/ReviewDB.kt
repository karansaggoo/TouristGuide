package com.example.touristguide.model

import java.util.*

class ReviewDB  (var id : String = UUID.randomUUID().toString(),
                 var email : String = "",
                 var name : String = "",
                 var review : String = "",) {
}