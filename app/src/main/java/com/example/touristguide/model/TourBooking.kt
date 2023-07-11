package com.example.touristguide.model

import java.util.*

class TourBooking(var id : String = UUID.randomUUID().toString(),var cusName:String="", var cusEmail:String="", var guideName:String="", var guideEmail:String="", var bookDate:String="", var people:String="") {
    override fun toString(): String {
        return "TourBooking(id='$id', cusName='$cusName', cusEmail='$cusEmail', guideName='$guideName', guideEmail='$guideEmail', bookDate='$bookDate', people='$people')"
    }
}