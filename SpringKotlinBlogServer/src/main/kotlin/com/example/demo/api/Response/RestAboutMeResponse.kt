package com.example.demo.api.Response

data class RestAboutMeResponse(
        val nickName: String?,
        val email: String?,
        val phone: String?,
        val aboutMe: String?,
        val picture: String?,
        val icon: String?
) {
}