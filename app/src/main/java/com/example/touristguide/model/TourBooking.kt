package com.example.touristguide.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
class TourBooking(var id : String = UUID.randomUUID().toString(),var cusName:String="", var cusEmail:String="", var guideName:String="", var guideEmail:String="", var bookDate:String="", var people:String=""):Parcelable {
    override fun toString(): String {
        return "TourBooking(id='$id', cusName='$cusName', cusEmail='$cusEmail', guideName='$guideName', guideEmail='$guideEmail', bookDate='$bookDate', people='$people')"
    }
}