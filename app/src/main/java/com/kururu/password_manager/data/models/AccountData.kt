package com.kururu.password_manager.data.models



data class AccountData(
    val id :Int ,
    val email: String,
    val password:String ,
    val icon :Int ,
    val typeName:String ,
    val comment :String
)
