package com.example.horoscopefun.ui.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.horoscopefun.domian.model.MHoroscope
import com.example.horoscopefun.domian.repository.HoroscopeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HoroscopeRepository
) : ViewModel() {

   private val _horescope = MutableStateFlow<List<MHoroscope>>(emptyList())
   val horescope: StateFlow<List<MHoroscope>> = _horescope

   init {
      viewModelScope.launch {
         _horescope.value = repository.getAllHoroscope()
      }

   }
}