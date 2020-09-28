package com.reem.android.finalmovieapp.data.network
import com.reem.android.finalmovieapp.data.models.remote.GetMoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


private const val apiKey ="d78d306988042530d99e166c3cb00017"
interface ApiServices {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "d78d306988042530d99e166c3cb00017",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "d78d306988042530d99e166c3cb00017",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>



}