package com.example.horoscopefun.ui.view.paimistry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.horoscopefun.databinding.FragmentPaimistryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaimistryFragment : Fragment() {

   companion object {

      private const val CAMERA_PERMISSION = android.Manifest.permission.CAMERA
   }

   private var _binding: FragmentPaimistryBinding? = null
   private val binding by lazy { _binding!! }
   private val viewModel by viewModels<PaimistryViewModel>()

   private val requestPermissionLauncher = registerForActivityResult(
       ActivityResultContracts.RequestPermission()
   ) { isGrand ->
      if (isGrand) {
         //starmCamera
         startCamera()
      } else {
         Toast
             .makeText(requireContext(), "you must provide access to the camera", Toast.LENGTH_LONG)
             .show()
      }
   }

   private fun startCamera() {
      val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
      cameraProviderFuture.addListener({
                                          val cameraProvider = cameraProviderFuture.get()

                                          val preview = Preview.Builder()
                                              .build()
                                              .also {
                                                 it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                                              }

                                          val cameraSelecter = CameraSelector.DEFAULT_BACK_CAMERA
                                          try {
                                             cameraProvider.unbindAll()
                                             cameraProvider.bindToLifecycle(
                                                 this,
                                                 cameraSelecter,
                                                 preview
                                             )
                                          } catch (ex: Exception) {
                                             Log.e("TAG", "startCamera: ${ex.message}")
                                          }
                                       }, ContextCompat.getMainExecutor(requireContext()))
   }

   override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View {
      _binding = FragmentPaimistryBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      if (checkCameraPermission()) {
         startCamera()
      } else {
         requestPermissionLauncher.launch(CAMERA_PERMISSION)
      }
   }

   private fun checkCameraPermission(): Boolean {
      return PermissionChecker.checkSelfPermission(
          requireContext(),
          CAMERA_PERMISSION
      ) == PermissionChecker.PERMISSION_GRANTED
   }

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }

}