package com.reem.android.finalmovieapp.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.reem.android.finalmovieapp.R
import com.reem.android.finalmovieapp.data.models.remote.*
import com.reem.android.finalmovieapp.data.models.ui.Movie
import com.reem.android.finalmovieapp.data.repository.MoviesRepository
import com.reem.android.finalmovieapp.ui.MoviesAdapter
import com.reem.android.finalmovieapp.ui.movie.popular.PopularViewModel
import kotlinx.android.synthetic.main.toprated_fragment.*

class TopRatedFragment: Fragment(){

    private val mainViewModel: TopRatedViewModel by viewModels()

    private lateinit var topRatedMovies: RecyclerView
    private lateinit var topRatedMoviesAdapter: MoviesAdapter
    private lateinit var topRatedMoviesLayoutMgr: LinearLayoutManager

    private var topRatedMoviesPage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mainViewModel.getTopRatedMovies().observe(viewLifecycleOwner, Observer {
            recycle_toprated_movies.adapter = MoviesAdapter(it) { movie -> showMovieDetails(movie) }
        })
        return inflater.inflate(R.layout.toprated_fragment, container, false)
    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this.context, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        startActivity(intent)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        topRatedMovies = recycle_toprated_movies
        topRatedMoviesLayoutMgr = GridLayoutManager(this.context, 2)
        topRatedMovies.layoutManager = topRatedMoviesLayoutMgr
        topRatedMoviesAdapter = MoviesAdapter(mutableListOf()) { movie -> showMovieDetails(movie) }
        println(topRatedMoviesAdapter)
        topRatedMovies.adapter = topRatedMoviesAdapter
        getTopRatedMovies()
    }

    private fun getTopRatedMovies() {
        MoviesRepository.getTopRatedMovies(
            topRatedMoviesPage )
        attachTopRatedMoviesOnScrollListener()
    }

    private fun attachTopRatedMoviesOnScrollListener() {
        topRatedMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = topRatedMoviesLayoutMgr.itemCount
                val visibleItemCount = topRatedMoviesLayoutMgr.childCount
                val firstVisibleItem = topRatedMoviesLayoutMgr.findFirstVisibleItemPosition()

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    topRatedMovies.removeOnScrollListener(this)
                    topRatedMoviesPage++
                    getTopRatedMovies()
                }
            }
        })
    }

}