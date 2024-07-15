package com.example.trippy_android.User

import com.google.gson.annotations.SerializedName

sealed class UserResponse {
    data class CommonResponse(
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code: String,
        @SerializedName("message") val message: String
    ) : UserResponse()

    data class SignUpResponse(
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code: String,
        @SerializedName("message") val message: String,
        @SerializedName("result") val result: SignUpResult
    ) : UserResponse() {
        data class SignUpResult(
            @SerializedName("idx") val idx: Int,
            @SerializedName("email") val email: String,
            @SerializedName("nickName") val nickName: String
        )
    }

    data class LoginResponse(
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code: String,
        @SerializedName("message") val message: String,
        @SerializedName("result") val result: LoginResult
    ) : UserResponse() {
        data class LoginResult(
            @SerializedName("memberId") val memberId: String,
            @SerializedName("accessToken") val accessToken: String,
            @SerializedName("role") val role: String
        )
    }
}