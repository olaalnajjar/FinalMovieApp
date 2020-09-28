

package com.reem.android.finalmovieapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.reem.android.finalmovieapp.data.models.ui.Movie
import com.reem.android.finalmovieapp.data.models.ui.MovieT

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Query("SELECT * FROM movies_table")
    fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM moviesT_table")
    fun getAllMoviesT(): List<MovieT>


}