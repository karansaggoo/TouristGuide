package com.example.touristguide.model

class Guide(var email:String="",var name:String="",var tel:Int=0,var loc:String="",var desc:String="",var imageUri:String="") {
    override fun toString(): String {
        return "Guide(email='$email', name='$name', tel=$tel, loc='$loc', desc='$desc', imageUri='$imageUri')"
    }
}