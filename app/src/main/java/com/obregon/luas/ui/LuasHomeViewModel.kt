package com.obregon.luas.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.obregon.luas.data.QueryResult
import com.obregon.luas.data.repository.LuasRepository
import com.obregon.luas.data.response.StopInfo
import kotlinx.coroutines.launch
import java.io.IOException
import java.lang.Exception
import java.time.LocalTime
import java.util.*
import javax.inject.Inject

interface TimeHelper{
    fun isMorning():Boolean
}

class TimeHelperImpl @Inject constructor():TimeHelper {
    private val time: LocalTime= LocalTime.now()

    override fun isMorning():Boolean {
        if (time.isAfter(LocalTime.MIDNIGHT) && time.isBefore(LocalTime.NOON)){
            return true
        }
        return false
    }
}

data class StopFilter(val stopName:String,val direction:String)

class StopCodeProvider @Inject constructor(private val timeHelper: TimeHelper){
    fun getStopCode():StopFilter{
        if(timeHelper.isMorning()){
          return StopFilter("CON","Outbound")
        }
        return StopFilter("STI","Inbound")
    }
}
data class Tram(val station:String,val dueMins:String,val rowType: RowType )
data class UiData constructor(val stationName:String, val direction:String, val
                              statusMessage:String, val lastUpdated:String, val trams:List<Tram>)
enum class RowType{
    TYPE_HEADER,TYPE_TITLE,TYPE_ROW

}

class LuasHomeViewModel @ViewModelInject constructor(private val luasRepository: LuasRepository,
                                                     private val stopCodeProvider: StopCodeProvider): ViewModel() {

    private val _error = MutableLiveData<String>()
    private val _uiData = MutableLiveData<UiData>()
    val uiData:LiveData<UiData> = _uiData

    init {
        loadData()
    }

    fun loadData(){
        viewModelScope.launch {
            val stopFilter= stopCodeProvider.getStopCode()
            when(val queryResult=luasRepository.getStationDetails(stopFilter.stopName)){
                is QueryResult.Success -> _uiData.value=processResult(queryResult.stopInfo,stopFilter)
                is QueryResult.Failure -> processError(queryResult.exception)
            }
        }
    }

    private fun processError(exception: Exception){
        when(exception){
            is IOException -> _error.value="A network error occurred"
            else -> _error.value="An unknown error occurred"
        }
    }

    private fun processResult(result: StopInfo,stopFilter:StopFilter):UiData{

        val filtered=result.direction?.filter { it.name.equals("Outbound") }
        val trams=filtered?.get(0)?.tram

        val direction=filtered?.get(0)?.name
        var size=filtered?.get(0)?.tram?.size
        if(size==null){
            size=0
        }

        val tramList= ArrayList<Tram>(size)
        tramList.add(Tram(
            "Destination",
            "Due (mins)",
            RowType.TYPE_TITLE))

        trams?.forEach{
            tramList.add(Tram(
                it.destination as String,
                it.dueMins as String,
                RowType.TYPE_ROW))
        }

        val lastUpdated="(Last updated at: " + result.created as String + ")"
        val directionSubtitle= "Direction: " + direction as String
        val serviceStatus= result.message as String

        return UiData(stationName = result.stop as String,
            statusMessage = serviceStatus,
            lastUpdated = lastUpdated,
            direction = directionSubtitle,
            trams=tramList)

    }
}

