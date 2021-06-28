package com.example.projpaint3.API

import com.example.projpaint3.Model.Clima
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ClimaService {

    @GET("weather?key=a4e90a3a&city_name=Rio%20de%20Janeiro,RJ")
    fun getRJ(): Call<Clima>

    @GET("{id}")
    fun showRJ(@Path("id") id: String): Call<Clima>

}