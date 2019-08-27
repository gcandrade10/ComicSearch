package com.example.comicsearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.comicsearch.R
import com.squareup.picasso.Picasso

class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_movie, parent, false)) {
    private var mTitleView: TextView? = null
    private var mImageView: ImageView? = null
    private var mSpinner: ProgressBar? = null

    init {
        mTitleView = itemView.findViewById(R.id.textViewName)
        mImageView = itemView.findViewById(R.id.imageView)
        mSpinner = itemView.findViewById(R.id.progressBar)

    }

    fun bind(item: Item) {
        if (item.spinner) {
            mTitleView?.visibility = View.GONE
            mImageView?.visibility = View.GONE
            mSpinner?.visibility = View.VISIBLE
        } else {
            mTitleView?.text = item.movie.name
            Picasso.get().load(item.movie.image.thumb_url).into(mImageView)
        }
    }
}