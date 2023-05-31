package com.example.georunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("id") cityId: Int,
        @Query("appid") apiKey: String

    ): Response<WeatherData>
}
