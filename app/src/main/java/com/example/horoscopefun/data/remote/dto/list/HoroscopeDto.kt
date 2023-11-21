package com.example.horoscopefun.data.remote.dto.list


import com.google.gson.annotations.SerializedName

data class HoroscopeDto(
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = ""
)