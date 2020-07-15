package com.obregon.luas

import com.obregon.luas.ui.HomeScreen.TimeHelper
import com.obregon.luas.ui.HomeScreen.TimeHelperImpl
import com.obregon.luas.ui.HomeScreen.TimeProvider
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.time.LocalTime

@RunWith(JUnit4::class)
class TimeHelperTest{

    private val mockTimeProvider=mock(TimeProvider::class.java)
    private lateinit var timeHelper:TimeHelper
    @Before
    fun setup(){
        timeHelper=TimeHelperImpl()
    }

     @Test
     fun verifyMorningIsTrue(){
         var localTime:LocalTime= LocalTime.MIDNIGHT
         localTime=localTime.plusHours(1L)
         `when`(mockTimeProvider.getLocalTimeNow()).thenReturn(localTime)
         assertTrue(timeHelper.isMorning(localTime))
     }

    @Test
    fun verifyAfterNoonIsFalse(){
        var localTime:LocalTime= LocalTime.NOON
        localTime=localTime.plusHours(1L)
        `when`(mockTimeProvider.getLocalTimeNow()).thenReturn(localTime)
        assertFalse(timeHelper.isMorning(localTime))
    }

    @Test
    fun verifyNoonIsFalse(){
        val localTime:LocalTime= LocalTime.NOON
        `when`(mockTimeProvider.getLocalTimeNow()).thenReturn(localTime)
        assertFalse(timeHelper.isMorning(localTime))
    }

    @Test
    fun verifyMidnightIsFalse(){
        val localTime:LocalTime= LocalTime.MIDNIGHT
        `when`(mockTimeProvider.getLocalTimeNow()).thenReturn(localTime)
        assertFalse(timeHelper.isMorning(localTime))
    }

}