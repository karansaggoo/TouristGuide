package com.example.touristguide.adapter

import com.example.touristguide.model.Guide

    interface onGuideClickListener {
        fun onItemClickListener(place_id:String , guide:Guide)

    }
