package com.example.touristguide.model

class Message( var text: String? = null,
               var name: String? = null,
               var photoUrl: String? = null) {



    fun  Text():String?{
        return text
    }
    fun Name():String?{
        return name
    }

}