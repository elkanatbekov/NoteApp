package com.example.noteapp.ui.board

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.noteapp.databinding.ItemBoardBinding
import com.example.noteapp.models.Board
import com.example.noteapp.ui.Prefs

class BoardAdapter(val context: Context, val navController: NavController, val data: ArrayList<Board>) :
    Adapter<BoardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardAdapter.ViewHolder {
        return ViewHolder(
            ItemBoardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BoardAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        item.lottieFileName?.let {
            holder.binding.lottieView.setAnimation(it)
            holder.binding.lottieView.playAnimation()
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(val binding: ItemBoardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(board: Board) {
            binding.textTitle.text = board.title
            if (position == data.lastIndex /*list.size - 1*/) {
                binding.btnStart.visibility = View.VISIBLE
            } else {
                binding.btnStart.visibility = View.INVISIBLE
            }
            binding.btnStart.setOnClickListener {
                Prefs(context).saveState()
                navController.navigateUp()
            }
        }
    }
}