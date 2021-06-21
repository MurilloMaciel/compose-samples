package com.example.profilecardlayout

data class UserProfile(
    val name: String,
    val status: Boolean,
    val drawableId: Int
)

val userProfileList = arrayListOf(
    UserProfile(
        name = "John Doe",
        status = true,
        drawableId = R.drawable.profile_picture
    ),
    UserProfile(
        name = "Anna Joans",
        status = false,
        drawableId = R.drawable.profile_picture
    )
)