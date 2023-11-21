package com.example.horoscopefun.ui.view.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.horoscopefun.R
import com.example.horoscopefun.databinding.HoroscopeItemBinding
import com.example.horoscopefun.domian.model.MHoroscope

/**
 * @author : Mingaleev D
 * @data : 20.11.2023
 */

class HoroscopeAdapter(
    private val onClickItem: (MHoroscope) -> Unit
) : ListAdapter<MHoroscope, HoroscopeAdapter.MyViewHolder>(diff) {

   private companion object {

      val diff = object : DiffUtil.ItemCallback<MHoroscope>() {
         override fun areItemsTheSame(oldItem: MHoroscope, newItem: MHoroscope) =
             oldItem::class == newItem::class

         override fun areContentsTheSame(oldItem: MHoroscope, newItem: MHoroscope) =
             oldItem == newItem

      }
   }

   inner class MyViewHolder(val binding: HoroscopeItemBinding) :
       RecyclerView.ViewHolder(binding.root) {

      fun bind(itemModel: MHoroscope, onClickItem: (MHoroscope) -> Unit) {
         with(binding) {
            //text
            tvTitle.text = itemModel.name
            //img
            ivHoroscope.loadImage(itemModel.icon)

            //clickItem
            parent.setOnClickListener {
               //onClickItem(itemModel)
               startRotationAnimation(
                   view = binding.ivHoroscope,
                   newLanbda = { onClickItem(itemModel) }
               )
            }
         }
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
       MyViewHolder(
           HoroscopeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
       )

   override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
      val item = getItem(position) ?: return

      holder.bind(item, onClickItem)
   }
}

fun ImageView.loadImage(url: String) {
   val circularProgressDrawable = CircularProgressDrawable(this.context)
   circularProgressDrawable.strokeWidth = 5f
   circularProgressDrawable.centerRadius = 30f
   circularProgressDrawable.start()
   Glide.with(this).load(url).placeholder(circularProgressDrawable)
       .error(com.google.android.material.R.drawable.mtrl_ic_error).into(this)
}

private fun startRotationAnimation(view: View, newLanbda: () -> Unit) {
   view.animate().apply {
      duration = 500
      interpolator = LinearInterpolator()
      rotationBy(360f)
      withEndAction(newLanbda)
      start()
   }
}