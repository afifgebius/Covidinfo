package com.example.covidinfo.service

import com.example.covidinfo.model.CountriesItem
import com.example.covidinfo.model.ResponseCovid
import okhttp3.OkHttpClient
import okio.Timeout
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.nio.file.attribute.AclEntry.newBuilder
import java.util.concurrent.TimeUnit

object APIservice {

    private val client: OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.covid19api.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getServiceApiService() = retrofit.create(covidCountry::class.java)
    fun getServiceInfoCovid() = retrofit.create(covidCountry::class.java)
}

interface covidCountry {
    @GET("summary")
    fun getAllDataNegara(): Call<ResponseCovid>
}

interface InfoService {
    @GET
    fun getInfoService(@Url url: String?): Call<List<CountriesItem>>
}