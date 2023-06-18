package com.example.touristguide.model

class WishListPlace(var name:String="",var icon:String="",var place_id:String="",var rating :Float?=null) {
    override fun toString(): String {
        return "WishListPlace(name='$name', icon='$icon', place_id='$place_id', rating=$rating)"
    }
}