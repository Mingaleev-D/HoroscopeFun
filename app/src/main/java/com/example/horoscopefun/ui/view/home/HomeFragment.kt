package com.example.horoscopefun.ui.view.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.horoscopefun.R
import com.example.horoscopefun.databinding.FragmentHomeBinding
import com.example.horoscopefun.ui.view.home.adapter.HoroscopeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

   private var _binding: FragmentHomeBinding? = null
   private val binding by lazy { _binding!! }
   private val viewModel by viewModels<HomeViewModel>()
   private lateinit var homeAdapter: HoroscopeAdapter

   override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View {
      _binding = FragmentHomeBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      initUI()

   }

   private fun initUI() {
      initList()
      InitState()
   }

   private fun initList() {
      homeAdapter = HoroscopeAdapter(
          onClickItem = {
             //Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()

             findNavController().navigate(
                 HomeFragmentDirections.actionHomeFragmentToDetailActivity(it.name)
             )
          }
      )
      binding.rvHoroscope.layoutManager = GridLayoutManager(context, 2)
      binding.rvHoroscope.adapter = homeAdapter
   }

   private fun InitState() {
      lifecycleScope.launch {
         repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.horescope.collect {
               Log.d("TAG", "InitState: ${it.toString()}")
               homeAdapter.submitList(it)
            }
         }
      }

   }

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }


}