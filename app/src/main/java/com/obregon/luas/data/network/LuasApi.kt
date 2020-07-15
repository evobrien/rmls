package com.obregon.luas.data.network

import com.obregon.luas.data.response.StopInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface LuasApi {
    @GET("/xml/get.ashx?action=forecast&encrypt=false")
    suspend fun fetchLuasInfo(@Query("stop") stop:String): StopInfo
}