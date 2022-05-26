package com.example.smalltalk.Profile

import android.graphics.drawable.Drawable

class Profile (

    val name: String,
    val photos: Int,
    var followers: Int,
    val following: Int,
    val email: String,
    val phoneNumber: String,
    val picture: Drawable?,
    val pictureResource: Int
)