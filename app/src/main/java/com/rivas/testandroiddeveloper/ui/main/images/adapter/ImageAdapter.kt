package com.rivas.testandroiddeveloper.ui.main.images.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.rivas.testandroiddeveloper.databinding.MovieRvViewBinding

class ImageAdapter :
    RecyclerView.Adapter<MainViewHolder>() {

    private var urisList = mutableListOf<Uri>()

    @SuppressLint("NotifyDataSetChanged")
    fun setUris(movie: List<Uri>) {
        this.urisList = movie.toMutableList()
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieRvViewBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val uri = urisList[position]
        holder.binding.tvName.isVisible = false
        holder.binding.textViewProgress.isVisible = false
        holder.binding.progressBar.isVisible = false
        holder.binding.tvReleaseDate.isVisible = false
        holder.binding.view.isVisible = false
        holder.binding.imgMovie.setImageURI(uri)
    }

    override fun getItemCount(): Int {
        return urisList.size
    }
}

class MainViewHolder(val binding: MovieRvViewBinding) : RecyclerView.ViewHolder(binding.root)