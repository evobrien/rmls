package com.obregon.luas.ui.HomeScreen

import java.time.LocalTime
import javax.inject.Inject

interface TimeProvider{
    fun getLocalTimeNow():LocalTime
}

interface TimeHelper{
    fun isMorning(time:LocalTime):Boolean
}

class TimeHelperImpl @Inject constructor():
    TimeHelper {
    override fun isMorning(time: LocalTime ):Boolean {
        if (time.isAfter(LocalTime.MIDNIGHT) && time.isBefore(
                LocalTime.NOON
            )){
            return true
        }
        return false
    }
}

class TimeProviderImpl @Inject constructor():TimeProvider{
    override fun getLocalTimeNow(): LocalTime {
        return LocalTime.now()
    }

}

