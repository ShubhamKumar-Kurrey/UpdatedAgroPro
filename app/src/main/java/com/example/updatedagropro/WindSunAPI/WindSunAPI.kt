package com.example.updatedagropro.WindSunAPI

import com.example.updatedagropro.network.SensorData
import com.example.updatedagropro.network.SunWindData
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface WindSunAPI {
    @FormUrlEncoded
    @POST("slave_sensor")
    suspend fun getSunWindDatavalue(
        @Field("slave_id") id: String
    ): SunWindData
}

object APISW {
    var okHttpClient = OkHttpClient.Builder()
        .readTimeout(2, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()

    private val retrofit: WindSunAPI by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.4.1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WindSunAPI::class.java)
    }

    suspend fun getSunWindDatavalue(id: String): Result<SunWindData> {
        return try {
            Result.success(retrofit.getSunWindDatavalue(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}