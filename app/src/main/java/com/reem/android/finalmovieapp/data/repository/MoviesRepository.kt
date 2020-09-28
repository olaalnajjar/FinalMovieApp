package com.reem.android.finalmovieapp.data.repository

import com.reem.android.finalmovieapp.data.models.remote.GetMoviesResponse


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.reem.android.finalmovieapp.data.database.AppDatabase
import com.reem.android.finalmovieapp.data.models.ui.Movie
import com.reem.android.finalmovieapp.data.network.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRepository {

    private val api: ApiServices
    private val tag: String = MoviesRepository::class.java.simpleName

    private lateinit var dataBase: AppDatabase
    private val popularMoviesList: MutableList<Movie> = mutableListOf()
    private val topRatedMoviesList: MutableList<Movie> = mutableListOf()


    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiServices::class.java)
    }


    fun getPopularMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) : MutableLiveData<List<Movie>> {
        val moviesListLiveData: MutableLiveData<List<Movie>> = MutableLiveData()

        if (popularMoviesList.size > 0) {
            moviesListLiveData.postValue(popularMoviesList)
            return moviesListLiveData
        }else if (getLocalMovies().isNotEmpty()) {
            popularMoviesList.addAll(getLocalMovies())
            moviesListLiveData.postValue(popularMoviesList)
        }


        api.getPopularMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {

                        val remoteMoviesList: List<Movie> =
                            response.body()?.movies ?: listOf()
                        popularMoviesList.addAll(remoteMoviesList)
                        dataBase.getMoviesDao().insertAll(popularMoviesList)
                        moviesListLiveData.postValue(popularMoviesList)

                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                    Log.e(tag, t.message.toString())

                }
            })
        return moviesListLiveData

    }

    fun getTopRatedMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) :MutableLiveData<List<Movie>> {
        val moviesListLiveData: MutableLiveData<List<Movie>> = MutableLiveData()

        if (topRatedMoviesList.size > 0) {
            moviesListLiveData.postValue(topRatedMoviesList)
            return moviesListLiveData
        }

        api.getTopRatedMovies(page = page)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(
                    call: Call<GetMoviesResponse>,
                    response: Response<GetMoviesResponse>
                ) {
                    if (response.isSuccessful) {

                        val remoteMoviesList: List<Movie> = response.body()?.movies ?: listOf()
                        topRatedMoviesList.addAll(remoteMoviesList)
                        moviesListLiveData.postValue(topRatedMoviesList)



                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    onError.invoke()
                    Log.e(tag, t.message.toString())

                }
            })
        return moviesListLiveData

    }
    private fun getLocalMovies(): List<Movie> {
        return dataBase.getMoviesDao().getAllMovies()
    }


    }

