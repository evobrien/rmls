package com.obregon.luas.data

import com.obregon.luas.data.response.StopInfo

sealed class QueryResult {
    data class Success(val stopInfo: StopInfo) : QueryResult()
    data class Failure(val exception: Exception) : QueryResult()
}