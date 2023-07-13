package com.example.touristguide.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
class TourBooking(var id : String = UUID.randomUUID().toString(),var ncusName:String="", var cusEmail:String="",
                  var guideName:String="", var tel:String="", var guideEmail:String="", var bookingDate:String="",
                  var numOfPMember:String="",var paymentMode:String="" ,var cardName:String="",var cardNumber:String="",var card_cvv:String="",
                  var card_date:String=""):Parcelable {
    override fun toString(): String {
        return "TourBooking(id='$id', ncusName='$ncusName', cusEmail='$cusEmail', guideName='$guideName', tel='$tel', guideEmail='$guideEmail', bookingDate='$bookingDate', numOfPMember='$numOfPMember', paymentMode='$paymentMode', cardName='$cardName', cardNumber='$cardNumber', card_cvv='$card_cvv', card_date='$card_date')"
    }
}

