package com.example.comicsearch.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.comicsearch.R
import com.squareup.picasso.Picasso


class ListAdapter(private val onClickListener: (View, Movie) -> Unit) : RecyclerView.Adapter<MovieViewHolder>() {
    private val spinner = Item(spinner = true)
    private var posSpinner = 0
    private var mList = mutableListOf<Item>(spinner)

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item: Item = mList[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { view ->
            onClickListener.invoke(view, item.movie)
        }
    }

    override fun getItemCount(): Int = mList.size

    override fun getItemId(position: Int): Long {
        val product = mList.get(position)
        return product.movie.id.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun add(movies: List<Movie>):Boolean {
        removeSprinner()
        movies.forEachIndexed { index, movie ->
            mList.add(posSpinner + index, Item(movie = movie))
        }
        if (movies.isNotEmpty()) {
            addSpiner()
            notifyDataSetChanged()
            return true
        }
        notifyDataSetChanged()
        return false
    }

    //
    fun addSpiner() {
        posSpinner = mList.size
        mList.add(posSpinner, spinner)
    }

    fun removeSprinner() {
        mList.removeAt(posSpinner)
        notifyItemRemoved(posSpinner)
    }
}


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