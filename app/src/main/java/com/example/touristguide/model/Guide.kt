package com.example.touristguide.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class Guide(var id : String = UUID.randomUUID().toString(), var email:String="",var name:String="",var tel:String="",var loc:String="",var desc:String="",var imageUri:String="",var price:String=""):Parcelable {
    override fun toString(): String {
        return "Guide(id='$id', email='$email', name='$name', tel='$tel', loc='$loc', desc='$desc', imageUri='$imageUri', price='$price')"
    }
}