package com.ryubi.hellogames

import retrofit2.Call
import retrofit2.http.GET

interface WSList {

    @GET("/api/game/list")
    fun getAllGames() : Call<List<Game>>
}