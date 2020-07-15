package com.obregon.luas

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doAnswer
import com.obregon.luas.data.network.LuasApi
import com.obregon.luas.data.repository.LuasRepository
import com.obregon.luas.data.repository.LuasRepositoryImpl
import com.obregon.luas.data.response.Direction
import com.obregon.luas.data.response.StopInfo
import com.obregon.luas.data.response.Tram
import com.obregon.luas.ui.HomeScreen.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.io.IOException
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest{


    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private var repository=mock(LuasRepository::class.java)
    private var stopCodeProvider=mock(StopFilterProvider::class.java)
    private val mockLuasApi: LuasApi =mock(LuasApi::class.java)
    private lateinit var viewModel:HomeScreenViewModel

    @Before
    fun setup(){
        repository= LuasRepositoryImpl(mockLuasApi)
    }

    @Test
    fun verifyExceptionHandledAsError()= coroutineScope.runBlockingTest{
        Mockito.`when`(mockLuasApi.fetchLuasInfo(anyOrNull())).doAnswer{throw IOException() }
        Mockito.`when`(stopCodeProvider.getCurrentStopFilter()).thenReturn(StopFilter("MAR", OUTBOUND))
        viewModel= HomeScreenViewModel(
            repository,
            stopCodeProvider
        )
        assertEquals(viewModel.errorData.value, NETWORK_ERROR)
    }

    @Test
    fun verifyGeneralExceptionHandledAsError()= coroutineScope.runBlockingTest{
        Mockito.`when`(mockLuasApi.fetchLuasInfo(anyOrNull())).doAnswer{throw Exception() }
        Mockito.`when`(stopCodeProvider.getCurrentStopFilter()).thenReturn(StopFilter("MAR", OUTBOUND))
        viewModel= HomeScreenViewModel(
            repository,
            stopCodeProvider
        )
        assertEquals(viewModel.errorData.value, UNKNOWN_ERROR)
    }

    @Test
    fun verifyHappyPath()=coroutineScope.runBlockingTest {
        val date = LocalDateTime.now()
        val trams= arrayListOf(Tram("someStaion","11"))
        val directionList=arrayListOf(Direction(OUTBOUND,trams))
        val template= StopInfo(created=date.toString(),stop="MAR",stopAbv = "MAR",message = "this is a message",direction=directionList)
        Mockito.`when`(mockLuasApi.fetchLuasInfo(anyOrNull())).thenReturn(template)
        Mockito.`when`(stopCodeProvider.getCurrentStopFilter()).thenReturn(StopFilter("MAR", OUTBOUND))
        viewModel= HomeScreenViewModel(
            repository,
            stopCodeProvider
        )

        val expected=getReturnData(template)
        assertEquals(expected,viewModel.uiData.value)
    }

    private fun getReturnData(stopInfo:StopInfo):UiData{
        val date = LocalDateTime.parse(stopInfo.created.toString()).toLocalTime()
        val lastUpdated = "(Last updated at: $date)"
        val directionSubtitle = "Direction: " +  stopInfo.direction?.get(0)?.name

        val tramList = ArrayList<TramData>()
        tramList.add(
            TramData(
                "Destination",
                "Due(mins)",
                RowType.TYPE_TITLE
            )
        )
        val tram= stopInfo.direction?.get(0)?.tram?.get(0)
        val tramData=TramData(tram?.destination.toString(),tram?.dueMins.toString(),RowType.TYPE_ROW)
        tramList.add(tramData)

       return UiData(
            stationName = stopInfo.stop as String,
            statusMessage = stopInfo.message as String,
            lastUpdated = lastUpdated,
            direction = directionSubtitle,
            tramData =tramList
        )
    }

    @Test
    fun verifyBadDataHandled()=coroutineScope.runBlockingTest{
        val template= StopInfo(created=null,stop=null,stopAbv = null,message = null,direction=null)
        Mockito.`when`(mockLuasApi.fetchLuasInfo(anyOrNull())).thenReturn(template)
        Mockito.`when`(stopCodeProvider.getCurrentStopFilter()).thenReturn(StopFilter("MAR", OUTBOUND))
        viewModel= HomeScreenViewModel(
            repository,
            stopCodeProvider
        )
        assertEquals(viewModel.errorData.value,DATA_ERROR)
    }

}