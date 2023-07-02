package com.example.touristguide.model

import java.util.*

class Guide(var id : String = UUID.randomUUID().toString(), var email:String="",var name:String="",var tel:Int=0,var loc:String="",var desc:String="",var imageUri:String="") {
    override fun toString(): String {
        return "Guide(id='$id', email='$email', name='$name', tel=$tel, loc='$loc', desc='$desc', imageUri='$imageUri')"
    }
}