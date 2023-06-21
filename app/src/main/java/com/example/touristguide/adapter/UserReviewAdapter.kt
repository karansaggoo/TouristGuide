package com.example.touristguide.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.touristguide.model.ReviewDB
import com.example.touristguide.databinding.UserReviewItemBinding


class UserReviewAdapter(context: Context, var data:ArrayList<ReviewDB>):
                            ArrayAdapter<ReviewDB>(context,0,data) {

//    override fun getItem(position: Int): ReviewDB {
//
//        return data[position]
//    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currrntReview = getItem(position)

        lateinit var itemReviewBinding:UserReviewItemBinding
        itemReviewBinding=UserReviewItemBinding.inflate(LayoutInflater.from(context),parent,false)

        var itemView = itemReviewBinding.root

        if(currrntReview != null){
            itemReviewBinding.tvHotel.setText(currrntReview.name)
            itemReviewBinding.tvReview.setText(currrntReview.review)

        }

        return itemView
    }
}