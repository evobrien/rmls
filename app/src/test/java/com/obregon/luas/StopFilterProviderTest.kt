package com.obregon.luas

import com.nhaarman.mockitokotlin2.anyOrNull
import com.obregon.luas.ui.HomeScreen.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito


@RunWith(JUnit4::class)
class StopFilterProviderTest {

    private lateinit var stopFilterProvider: StopFilterProvider
    private val mockTimeProvider= Mockito.mock(TimeProvider::class.java)
    private var  timeHelper: TimeHelper= Mockito.mock(TimeHelper::class.java)

    @Before
    fun setup(){
        stopFilterProvider= StopFilterProvider(timeHelper,timeProvider =mockTimeProvider )
    }

    @Test
    fun verifyMorningReturnsMAR(){
        Mockito.`when`(timeHelper.isMorning(anyOrNull())).thenReturn(true)
        Assert.assertTrue(stopFilterProvider.getCurrentStopFilter().stopName == StopFilterProvider::MAR.name)
        Assert.assertTrue(stopFilterProvider.getCurrentStopFilter().direction== OUTBOUND)
    }

    @Test
    fun verifyAfterNoonReturnsSTI(){
        Mockito.`when`(timeHelper.isMorning(anyOrNull())).thenReturn(false)
        Assert.assertTrue(stopFilterProvider.getCurrentStopFilter().stopName == StopFilterProvider::STI.name)
        Assert.assertTrue(stopFilterProvider.getCurrentStopFilter().direction== INBOUND)
    }

}