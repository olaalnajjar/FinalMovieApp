package com.reem.android.finalmovieapp.ui.movie.popular

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.reem.android.finalmovieapp.data.models.ui.Movie
import com.reem.android.finalmovieapp.data.repository.MoviesRepository

class PopularViewModel(application: Application) : AndroidViewModel(application) {


    fun getPopularMovies(): LiveData<MutableList<Movie>> {
        return MoviesRepository.getPopularMovies()
    }


}