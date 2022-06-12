package com.example.proauthentication.services


import com.example.proauthentication.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface MovieApiInterface {

    @GET("/3/movie/popular?api_key=d9ef5025270c00b83dafeb7f59b6b042")
    fun getMovieList(): Call<MovieResponse>
}