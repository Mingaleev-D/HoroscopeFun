package com.example.horoscopefun.data.repository

import android.util.Log
import com.example.horoscopefun.data.mapper.toDomainDetails
import com.example.horoscopefun.data.mapper.toDomainList
import com.example.horoscopefun.data.remote.ApiService
import com.example.horoscopefun.domian.model.MHoroscope
import com.example.horoscopefun.domian.model.MHoroscopeDetail
import com.example.horoscopefun.domian.repository.HoroscopeRepository
import javax.inject.Inject

/**
 * @author : Mingaleev D
 * @data : 20.11.2023
 */

class HoroscopeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : HoroscopeRepository {

   override suspend fun getAllHoroscope(): List<MHoroscope> {
      return apiService.getAllHoroscope().toDomainList()
   }

   override suspend fun getDetails(sigh: String): MHoroscopeDetail? {

      kotlin.runCatching {
         apiService.getDetailsHoroscope(sigh)
      }
          .onSuccess {
             return it.toDomainDetails()
          }
          .onFailure {
             Log.d("TAG", "getDetails: ${it.message}")
          }

      return null
      //return apiService.getDetailsHoroscope(sigh).toDomainDetails()
   }


}