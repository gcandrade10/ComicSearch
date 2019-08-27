package com.example.comicsearch.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.comicsearch.api.Movie
import com.example.comicsearch.regularMoviesNumber


class ListAdapter(movies: List<Movie>, private val onClickListener: (View, Movie) -> Unit) :
    RecyclerView.Adapter<MovieViewHolder>() {
    private val spinner = Item(spinner = true)
    private var posSpinner: Int = 0
    private var mList: MutableList<Item>

    init {
        setHasStableIds(true)
        mList = movies.map { Item(movie = it) }.toMutableList()
        addSpinner()
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

    fun add(movies: List<Movie>) {
        removeSpinner()
        movies.forEachIndexed { index, movie ->
            mList.add(posSpinner + index, Item(movie = movie))
        }
        if (movies.isNotEmpty()) {
            addSpinner()
            notifyDataSetChanged()
        }
        notifyDataSetChanged()
    }

    private fun addSpinner() {
        if (mList.size >= regularMoviesNumber) {
            posSpinner = mList.size
            mList.add(posSpinner, spinner)
        }
    }

    private fun removeSpinner() {
        if (mList.size > posSpinner && mList[posSpinner] == spinner) {
            mList.removeAt(posSpinner)
            notifyItemRemoved(posSpinner)
        }
    }
}

