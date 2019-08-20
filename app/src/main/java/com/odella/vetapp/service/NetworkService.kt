package com.odella.vetapp.service

import com.google.gson.GsonBuilder
import com.odella.vetapp.constants.BASE_URL
import com.odella.vetapp.model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface NetworkService {
    var token: String

    //LOGIN
    @Headers("Content-Type: application/json")
    @POST("login/request")
        fun login(@Body request: RequestBody): Call<Any>

    //GETS
    @GET("login/check")
        fun checkConnection(): Call<Void>

    @GET("pets")
    fun getPets(@Header("Authorization") token: String): Call<List<Pet>>

    @GET("vets")
    fun getVets(@Header("Authorization") token: String): Call<List<Vet>>

    @GET("med")
    fun getMeds(@Header("Authorization") token: String): Call<List<Med>>

    @GET("vacc")
    fun getVaccs(@Header("Authorization") token: String): Call<List<Vacc>>

    @GET("owners")
    fun getOwners(@Header("Authorization") token: String): Call<List<Owner>>

    //Static
    companion object Factory{
        var gson = GsonBuilder().setLenient().create()
        fun create(): NetworkService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(NetworkService::class.java)
        }
    }
}