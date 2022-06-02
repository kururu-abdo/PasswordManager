package com.kururu.password_manager.Events

sealed class AppEvents {
    class GoToDetailsScreen(var email:String ="",var passwrd: String=""):AppEvents()
    class FirstTimeEvent:AppEvents()

    data class Login(val pwd:String="" , var email:String ="",var passwrd: String=""):AppEvents()

            data class  Register (val  passwrd: String):AppEvents()




}