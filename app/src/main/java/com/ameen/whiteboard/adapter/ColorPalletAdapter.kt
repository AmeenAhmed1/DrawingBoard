package com.ameen.whiteboard.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ameen.whiteboard.databinding.ItemColorBinding

class ColorPalletAdapter(val context: Context) :
    RecyclerView.Adapter<ColorPalletAdapter.MyViewHolder>() {

    private val TAG = "ColorPalletAdapter"

    inner class MyViewHolder(val binding: ItemColorBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var _binding: ItemColorBinding? = null

    private val differCallBack = object : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }

    val diff = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        _binding = ItemColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(_binding!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentColor = diff.currentList[position]
        Log.i(TAG, "onBindViewHolder: $currentColor")

        holder.binding.cardColorPallet.apply {
            setCardBackgroundColor(currentColor)
            setOnClickListener { onColorPalletSelectedListener?.let { it(currentColor) } }
        }
    }

    override fun getItemCount(): Int = diff.currentList.size

    private var onColorPalletSelectedListener: ((Int) -> Unit)? = null
    fun onColorPalletSelected(listener: (Int) -> Unit) {
        onColorPalletSelectedListener = listener
    }
}