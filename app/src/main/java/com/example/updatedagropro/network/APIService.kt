package com.example.updatedagropro.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


interface APIService {
    @FormUrlEncoded
    @POST("slave_sensor")
    suspend fun getSensorData(
        @Field("slave_id") id: String
    ): SensorData
}

object API {
    var okHttpClient = OkHttpClient.Builder()
        .readTimeout(2, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    private val retrofit: APIService by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.4.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

    suspend fun getSensorData(id: String): Result<SensorData> {
        return try {
            Result.success(retrofit.getSensorData(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}