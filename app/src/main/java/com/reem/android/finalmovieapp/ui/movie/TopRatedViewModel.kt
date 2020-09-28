package com.reem.android.finalmovieapp.ui.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.reem.android.finalmovieapp.data.models.ui.Movie
import com.reem.android.finalmovieapp.data.repository.MoviesRepository

class TopRatedViewModel(application: Application) : AndroidViewModel(application) {

    fun getTopRatedMovies(): LiveData<MutableList<Movie>> {
        return MoviesRepository.getTopRatedMovies()
    }
}
