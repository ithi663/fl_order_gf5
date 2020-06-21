package com.randomgametpnv.login.entities

sealed class LoginState {
    data class Login(val accessToken: String, val tokenType: String): LoginState()
    object Logout: LoginState()
    object NoInternet: LoginState()
}