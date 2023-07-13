package com.example.touristguide.model

import java.util.*

class TourBooking(var id : String = UUID.randomUUID().toString(),var cusName:String="", var cusEmail:String="",
                  var guideName:String="", var guideTel:String="", var guideEmail:String="", var bookDate:String="",
                  var people:String="",var paymentMode:String="" ,var cardName:String="",var cardNumber:String="",var cardCVV:String="",
                  var cardExpiry:String="") {

}

