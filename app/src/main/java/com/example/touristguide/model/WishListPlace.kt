package com.example.touristguide.model

import java.util.*

class WishListPlace(var name:String="",var icon:String="",var place_id:String="",var rating :String="",var id: String = UUID.randomUUID().toString()) {
    override fun toString(): String {
        return "WishListPlace(name='$name', icon='$icon', place_id='$place_id', rating=$rating, id='$id')"
    }
}