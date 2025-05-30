package com.example.noteapp.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.noteapp.databinding.ItemNewsBinding
import com.example.noteapp.models.News
import java.io.Serializable

class NewsAdapter(
    private val onClick: (position: Int) -> Unit,
    private val onLongClick: (position: Int) -> Unit
) :
    Adapter<NewsAdapter.ViewHolder>() {

    private var list = arrayListOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        return ViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClick(position)
        }

        holder.itemView.setOnLongClickListener {
            onLongClick(position)
            true
        }

        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.GRAY)
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
        }

        holder.bind(list[position])
    }

    override fun getItemCount() = list.size


    fun addItem(news: Serializable?) {
        news?.let {
            list.add(0, it as News)
            notifyItemInserted(list.indexOf(news))
        }
    }

    fun getItem(it: Int): News {
        return list[it]
    }

    fun removeItem(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun getItems(list: List<News>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun items(list: List<News>) {
        this.list = list as ArrayList<News>
        notifyDataSetChanged()
    }


    inner class ViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(news: News) {
            binding.textTitle.text = news.title
            binding.textTime.text = news.createdAt


        }
    }
}