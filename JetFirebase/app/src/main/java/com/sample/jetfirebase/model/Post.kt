package com.sample.jetfirebase.model

data class Post(
    val id: String,
    val image: String,
    val title: String,
    val content: String,
    val address: String,
    val phone: String
){

    constructor() : this("", "", "", "", "", "")

}
