package com.example.touristguide.model


class Photo(var photo_reference: String){
    override fun toString(): String {
        return "Photo(photo_reference='$photo_reference')"
    }
}

class Result(var name:String,var icon:String,var place_id:String,var photos:List<Photo>?,var rating :Float?=null){
    override fun toString(): String {
        return "Result(name='$name', icon='$icon', place_id='$place_id', photos=$photos, rating=$rating)"
    }
}


class Review(var author_name:String,var relative_time_description:String , var text:String){
    override fun toString(): String {
        return "Review(author_name='$author_name', relative_time_description='$relative_time_description', text='$text')"
    }
}

class Timing(var weekday_text:List<String>){
    override fun toString(): String {
        return "Timing(weekday_text=$weekday_text)"
    }
}

class Detail(var name:String?=null,var formatted_address:String?=null,var formatted_phone_number:String?=null,var opening_hours:Timing?=null,var reviews:List<Review>?=null, var rating: Float?=null){
    override fun toString(): String {
        return "Detail(name=$name, formatted_address=$formatted_address, formatted_phone_number=$formatted_phone_number, opening_hours=$opening_hours, reviews=$reviews, rating=$rating)"
    }
}


class PlaceDetail(var result:Detail?){
    override fun toString(): String {
        return "PlaceDetail(results=$result)"
    }
}





class Place(var results:List<Result>) {
    override fun toString(): String {
        return "Place(results=$results)"
    }
}