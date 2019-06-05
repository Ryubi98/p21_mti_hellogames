package com.ryubi.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WSGame {

    @GET("/api/game/details")
    fun getGameDetails(@Query("game_id") game_id : Int) : Call<GameDetail>
}