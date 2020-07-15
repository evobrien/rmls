package com.obregon.luas

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doAnswer
import com.obregon.luas.data.QueryResult
import com.obregon.luas.data.network.LuasApi
import com.obregon.luas.data.repository.LuasRepository
import com.obregon.luas.data.repository.LuasRepositoryImpl
import com.obregon.luas.data.response.StopInfo
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.IOException


@RunWith(JUnit4::class)
class LuasRepositoryTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val mockLuasApi: LuasApi=mock(LuasApi::class.java)
    private lateinit var luasRepository:LuasRepository

    @Before
    fun setup(){
        luasRepository=LuasRepositoryImpl(mockLuasApi)
    }

    @Test
    fun verifyExceptionHandled()= runBlocking {
            //do answer is a workaround here
            `when`(mockLuasApi.fetchLuasInfo(anyOrNull())).doAnswer{throw IOException() }
            val queryResult:QueryResult=luasRepository.getStationDetails("MAR")
            assertTrue(queryResult is QueryResult.Failure)
        }

    @Test
    fun verifyQuerySuccessHappyPath()= runBlocking {
        val template= StopInfo(created="adate",stop="MAR",stopAbv = "MAR",message = "this is a message",direction=ArrayList())
        `when`(mockLuasApi.fetchLuasInfo(anyOrNull())).thenReturn(template)
        val queryResult:QueryResult=luasRepository.getStationDetails("MAR")
        assertTrue(queryResult is QueryResult.Success)
        when(queryResult){
            is QueryResult.Success -> assertTrue(queryResult.stopInfo == template)
            is QueryResult.Failure -> Assert.fail()
        }
    }

}