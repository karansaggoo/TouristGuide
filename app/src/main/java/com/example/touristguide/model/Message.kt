package com.example.touristguide.model

class Message(
                var sender_id:String?=null,
                var text: String? = null,
               var name: String? = null,
                var timeStamp:String?=null,
               var photoUrl: String? = null) {



    fun  Text():String?{
        return text
    }
    fun Name():String?{
        return name
    }
    fun timeStamp():String?{
        return timeStamp
    }

}