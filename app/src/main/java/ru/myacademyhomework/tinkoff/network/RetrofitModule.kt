package ru.myacademyhomework.tinkoff.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import ru.myacademyhomework.tinkoff.BuildConfig

object RetrofitModule {
    private val json: Json = Json{ignoreUnknownKeys = true}
    private val contentType = "application/json".toMediaType()
    private val client = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()
    val developersLifeApi: DevelopersLifeApi = retrofit.create()

}