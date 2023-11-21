package com.example.horoscopefun.domian.repository

import com.example.horoscopefun.domian.model.MHoroscope
import com.example.horoscopefun.domian.model.MHoroscopeDetail

/**
 * @author : Mingaleev D
 * @data : 20.11.2023
 */

interface HoroscopeRepository {

   suspend fun getAllHoroscope(): List<MHoroscope>
   suspend fun getDetails(sigh: String): MHoroscopeDetail?
}