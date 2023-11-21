package com.example.horoscopefun.ui.view.luck

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.horoscopefun.R
import com.example.horoscopefun.databinding.FragmentLuckBinding
import com.example.horoscopefun.ui.view.luck.listeners.OnSwipeTouchListener
import com.example.horoscopefun.ui.view.luck.providers.RandomCardProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LuckFragment : Fragment() {

   private var _binding: FragmentLuckBinding? = null
   private val binding by lazy { _binding!! }
   private val viewModel by viewModels<LuckViewModel>()

   @Inject
   lateinit var randomCardProvider: RandomCardProvider

   override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?,
       savedInstanceState: Bundle?
   ): View {
      _binding = FragmentLuckBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      initUI()

   }

   private fun initUI() {
      preparePrediction()
      initListeners()
   }

   private fun preparePrediction() {
      val currentLuck = randomCardProvider.getLucky()
      currentLuck?.let { luck ->
         binding.tvLucky.text = getString(luck.text)
         binding.ivLuckyCard.setImageResource(luck.image)
         binding.tvShare.setOnClickListener { shareResult(getString(luck.text)) }
      }
   }

   private fun shareResult(prediction: String) {
      val sendIntent: Intent = Intent().apply {
         action = Intent.ACTION_SEND
         putExtra(Intent.EXTRA_TEXT, prediction)
         type = "text/plain"
      }

      val shareIntent = Intent.createChooser(sendIntent, null)
      startActivity(shareIntent)
   }

   @SuppressLint("ClickableViewAccessibility")
   private fun initListeners() {
      //binding.ivRoulette.setOnClickListener { spinRoulette() }
      binding.ivRoulette.setOnTouchListener(object : OnSwipeTouchListener(requireContext()) {
         override fun onSwipeRight() {
            spinRoulette()
         }

         override fun onSwipeLeft() {
            spinRoulette()
         }
      })
   }

   private fun spinRoulette() {
      val random = java.util.Random()
      val degress = random.nextInt(1440) + 360

      val animator = ObjectAnimator.ofFloat(
          binding.ivRoulette,
          View.ROTATION,
          0f,
          degress.toFloat()
      )
      animator.duration = 2000
      animator.interpolator = DecelerateInterpolator()
      animator.doOnEnd { sliderCard() }
      animator.start()
   }

   private fun sliderCard() {
      val sliderUpAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
      sliderUpAnimation.setAnimationListener(object : Animation.AnimationListener {
         override fun onAnimationStart(animation: Animation?) {
            binding.reverse.isVisible = true
         }

         override fun onAnimationEnd(animation: Animation?) {
            growCard()
         }

         private fun growCard() {
            val growAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.grow)
            growAnimation.setAnimationListener(object : Animation.AnimationListener {
               override fun onAnimationStart(animation: Animation?) {}

               override fun onAnimationEnd(animation: Animation?) {
                  binding.reverse.isVisible = false
                  showPremonitionView()
               }

               private fun showPremonitionView() {
                  val dissappearAnimation = AlphaAnimation(1.0f, 0.0f)
                  dissappearAnimation.duration = 200

                  val appearAnimation = AlphaAnimation(0.0f, 1.0f)
                  appearAnimation.duration = 1000

                  dissappearAnimation.setAnimationListener(object : Animation.AnimationListener {
                     override fun onAnimationStart(animation: Animation?) {}

                     override fun onAnimationEnd(animation: Animation?) {
                        binding.preview.isVisible = false
                        binding.prediction.isVisible = true
                     }

                     override fun onAnimationRepeat(animation: Animation?) {}

                  })

                  binding.preview.startAnimation(dissappearAnimation)
                  binding.prediction.startAnimation(appearAnimation)
               }

               override fun onAnimationRepeat(animation: Animation?) {}

            })
            binding.reverse.startAnimation(growAnimation)
         }

         override fun onAnimationRepeat(animation: Animation?) {
            // TODO("Not yet implemented")
         }
      })

      binding.reverse.startAnimation(sliderUpAnimation)
   }

   override fun onDestroyView() {
      super.onDestroyView()
      _binding = null
   }

}
