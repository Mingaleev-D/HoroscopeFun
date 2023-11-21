package com.example.horoscopefun.ui.view.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import com.example.horoscopefun.databinding.ActivityDetailBinding
import com.example.horoscopefun.ui.view.home.adapter.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

   private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
   private val viewModel by viewModels<DetailsViewModel>()

   private val args: DetailActivityArgs by navArgs()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)

      initUI()
      viewModel.getDetailsId(args.type)
      // args.type
   }

   private fun initUI() {
      iniListener()
      initUiState()
   }

   private fun iniListener() {
      binding.ivBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
   }

   private fun initUiState() {
      lifecycleScope.launch {
         repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.horescopeDetails.collect {
               when (it) {
                  is HoroscopeDetailState.Error -> errorState()
                  HoroscopeDetailState.Loading -> loadingState()
                  is HoroscopeDetailState.Success -> successState(it)
               }
            }
         }
      }
   }

   private fun successState(horoscopeDetailState: HoroscopeDetailState.Success) {
      binding.pb.isVisible = false
      binding.tvTitle.text = horoscopeDetailState.sign
      binding.tvBody.text = horoscopeDetailState.prediction
      binding.ivDetail.loadImage(horoscopeDetailState.image)
   }

   private fun errorState() {
      binding.pb.isVisible = false
   }

   private fun loadingState() {
      binding.pb.isVisible = true
   }
}