package com.example.horoscopefun.data.remote

import com.example.horoscopefun.data.remote.dto.HoroscopeDetailsDto
import com.example.horoscopefun.data.remote.dto.list.HoroscopeDto
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author : Mingaleev D
 * @data : 20.11.2023
 */

interface ApiService {

   companion object {

      const val BASE_URL = "https://newastro.vercel.app/"
   }

   @GET("list/")
   suspend fun getAllHoroscope(): List<HoroscopeDto>

   @GET("{sigh}/")
   suspend fun getDetailsHoroscope(
       @Path("sigh") sigh: String
   ): HoroscopeDetailsDto
}