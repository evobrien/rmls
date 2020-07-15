package com.obregon.luas.data.repository

import com.obregon.luas.data.QueryResult
import com.obregon.luas.data.network.LuasApi
import timber.log.Timber
import javax.inject.Inject


interface LuasRepository{
    suspend fun getStationDetails(stopCode:String):QueryResult
}

class LuasRepositoryImpl @Inject constructor(val luasApi: LuasApi):LuasRepository{
    override suspend fun getStationDetails(stopCode:String):QueryResult {
       return try {
            val stopInfo=luasApi.fetchLuasInfo(stopCode)
            Timber.d(stopInfo.toString())
            QueryResult.Success(stopInfo)
        }catch(exception:Exception){
            Timber.e(exception)
            QueryResult.Failure(exception)
        }
    }
}

