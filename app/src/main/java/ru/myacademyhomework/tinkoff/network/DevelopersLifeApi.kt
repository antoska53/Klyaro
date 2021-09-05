package ru.myacademyhomework.tinkoff.network

import retrofit2.http.GET
import ru.myacademyhomework.tinkoff.model.GifModel

interface DevelopersLifeApi {
    @GET("random?json=true")
    suspend fun getRandomImage(): GifModel
}