package com.example.mhjg43.kotlinendlessrecycler.network

import com.example.mhjg43.kotlinendlessrecycler.MovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by mhjg43 on 6/2/2017.
 */
interface MoviesApi {
    @GET("movies.php")
    fun getMovies(@Query("index") index: Int): Call<List<MovieModel>>
}