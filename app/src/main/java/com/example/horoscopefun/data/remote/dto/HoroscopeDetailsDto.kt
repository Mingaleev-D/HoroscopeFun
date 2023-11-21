package com.example.horoscopefun.data.remote.dto


import com.google.gson.annotations.SerializedName

data class HoroscopeDetailsDto(
    @SerializedName("date")
    val date: String = "",
    @SerializedName("horoscope")
    val horoscope: String = "",
    @SerializedName("icon")
    val icon: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("sign")
    val sign: String = ""
)