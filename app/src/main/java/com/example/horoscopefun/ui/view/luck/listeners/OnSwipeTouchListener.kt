package com.example.horoscopefun.ui.view.luck.listeners

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * @author : Mingaleev D
 * @data : 21.11.2023
 */

open class OnSwipeTouchListener(context: Context) : View.OnTouchListener {

   companion object {
      private const val SWIPE_THRESHOLD = 100
      private const val SWIPE_VELOCITY_THRESHOLD = 100
   }

   private val gestureDetector: GestureDetector

   init {
      gestureDetector = GestureDetector(context, GestureListener())
   }

   override fun onTouch(v: View, event: MotionEvent): Boolean {
      return gestureDetector.onTouchEvent(event)
   }

   private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {


      override fun onDown(e: MotionEvent): Boolean {
         return true
      }

      override fun onFling(
          e1: MotionEvent?,
          e2: MotionEvent,
          velocityX: Float,
          velocityY: Float
      ): Boolean {
         var result = false
         try {
            val diffY = e2.y - e1!!.y
            val diffX = e2.x - e1.x
            if (Math.abs(diffX) > Math.abs(diffY)) {
               if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                  if (diffX > 0) {
                     onSwipeRight()
                  } else {
                     onSwipeLeft()
                  }
                  result = true
               }
            } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
               if (diffY > 0) {
                  onSwipeBottom()
               } else {
                  onSwipeTop()
               }
               result = true
            }
         } catch (exception: Exception) {
            exception.printStackTrace()
         }

         return result
      }


   }

   open fun onSwipeRight() {}

   open fun onSwipeLeft() {}

   open fun onSwipeTop() {}

   open fun onSwipeBottom() {}
}