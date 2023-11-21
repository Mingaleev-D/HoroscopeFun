package com.example.horoscopefun.domian.use_case

import com.example.horoscopefun.domian.model.MHoroscopeDetail
import com.example.horoscopefun.domian.repository.HoroscopeRepository
import javax.inject.Inject

/**
 * @author : Mingaleev D
 * @data : 21.11.2023
 */

class GetDetailsByIdUseCase @Inject constructor(
    private val horoscopeRepository: HoroscopeRepository
) {

   suspend operator fun invoke(sigh: String): MHoroscopeDetail? {
      return horoscopeRepository.getDetails(sigh)
   }
}