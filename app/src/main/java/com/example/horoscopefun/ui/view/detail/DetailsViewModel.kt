package com.example.horoscopefun.ui.view.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.horoscopefun.domian.model.MHoroscope
import com.example.horoscopefun.domian.model.MHoroscopeDetail
import com.example.horoscopefun.domian.repository.HoroscopeRepository
import com.example.horoscopefun.domian.use_case.GetDetailsByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author : Mingaleev D
 * @data : 20.11.2023
 */

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetailsByIdUseCase: GetDetailsByIdUseCase
) : ViewModel() {

   private val _stateDetails = MutableStateFlow<HoroscopeDetailState>(HoroscopeDetailState.Loading)
   val horescopeDetails: StateFlow<HoroscopeDetailState> = _stateDetails

   init {
      //      fun getDetails(sugnLString: String){
      //         viewModelScope.launch {
      //            _horescopeDetails.tryEmit(
      //                repository.getDetails(sugnLString)
      //            )
      //         }
      //      }
   }

   fun getDetailsId(sign: String) {
      viewModelScope.launch {
         _stateDetails.value = HoroscopeDetailState.Loading
         val result = withContext(Dispatchers.IO) { getDetailsByIdUseCase(sign) }
         if (result != null) {
            _stateDetails.value = HoroscopeDetailState.Success(
                prediction = result.horoscope,
                sign = result.sign,
                image = result.icon
                //horoscopeModel = result
            )
         }else{
            _stateDetails.value = HoroscopeDetailState.Error("Error")
         }
      }
   }

}

sealed class HoroscopeDetailState {
   data object Loading : HoroscopeDetailState()
   data class Error(val error: String) : HoroscopeDetailState()
   data class Success(
       val prediction: String,
       val sign: String,
       val  image:String
       // val horoscopeModel: MHoroscopeDetail
   ) : HoroscopeDetailState()
}

//data class HoroscopeDetailsState(
//    val loading:Boolean = false,
//    val error:String? = null,
//    val data:MHoroscopeDetail? = null
//)
