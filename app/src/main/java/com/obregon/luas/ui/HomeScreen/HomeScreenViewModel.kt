package com.obregon.luas.ui.HomeScreen

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.obregon.luas.data.QueryResult
import com.obregon.luas.data.repository.LuasRepository
import com.obregon.luas.data.response.StopInfo
import com.obregon.luas.data.response.Tram
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.Exception
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class StopFilter(val stopName:String,val direction:String)

const val OUTBOUND:String="Outbound"
const val INBOUND:String="Inbound"

class StopFilterProvider @Inject constructor(private val timeHelper: TimeHelper, private val timeProvider: TimeProvider){
    val MAR:String ="MAR"
    val STI:String ="STI"

    private val mapStopFilter:HashMap<String, StopFilter> = hashMapOf(MAR to StopFilter(
        MAR,
        OUTBOUND
    ),
        STI to StopFilter(
            STI,
            INBOUND
        )
    )

    fun getCurrentStopFilter(): StopFilter {
        if(timeHelper.isMorning(timeProvider.getLocalTimeNow())){
          return mapStopFilter[MAR] as StopFilter
        }
        return mapStopFilter[STI] as StopFilter
    }
}


const val NETWORK_ERROR:String="A network error occurred"
const val DATA_ERROR:String="A data processing error occurred"
const val UNKNOWN_ERROR:String="An unknown error occurred"

class HomeScreenViewModel @ViewModelInject constructor(private val luasRepository: LuasRepository,
                                                       private val stopFilterProvider: StopFilterProvider
): ViewModel() {

    private val _error = MutableLiveData<String>()
    private val _uiData = MutableLiveData<UiData>()
    val uiData:LiveData<UiData> = _uiData
    val errorData:LiveData<String> = _error

    init {
        loadData()
    }

    fun loadData(){
        viewModelScope.launch {
            val stopFilter= stopFilterProvider.getCurrentStopFilter()
            when(val queryResult=luasRepository.getStationDetails(stopFilter.stopName)){
                is QueryResult.Success -> processResult(queryResult.stopInfo,stopFilter)
                is QueryResult.Failure -> processError(queryResult.exception)
            }
        }
    }

    private fun processError(exception: Exception){
        when(exception){
            is IOException -> _error.value=NETWORK_ERROR
            else -> _error.value=UNKNOWN_ERROR
        }
    }

    private fun processResult(result: StopInfo, stopFilter: StopFilter) {
        try{
            val filtered = result.direction?.filter { it.name.equals(stopFilter.direction) }
            val trams = filtered?.get(0)?.tram
            val direction = filtered?.get(0)?.name.toString()

            val serviceStatus = result.message.toString()
            val date = LocalDateTime.parse(result.created.toString()).toLocalTime()
            val lastUpdated = "(Last updated at: $date)"
            val directionSubtitle = "Direction: $direction"
            val tramList = getTramList(trams)

            _uiData.value= UiData(
                stationName = result.stop.toString(),
                statusMessage = serviceStatus,
                lastUpdated = lastUpdated,
                direction = directionSubtitle,
                tramData = tramList
            )
        }catch(exception:Exception){
            _error.value=DATA_ERROR
        }
    }

    private fun getTramList(
        trams: List<Tram>?
    ): ArrayList<TramData> {
        val tramList = ArrayList<TramData>()
        tramList.add(
            TramData(
                "Destination",
                "Due(mins)",
                RowType.TYPE_TITLE
            )
        )

        trams?.forEach {
            tramList.add(
                TramData(
                    it.destination as String,
                    it.dueMins as String,
                    RowType.TYPE_ROW
                )
            )
        }
        return tramList
    }
}

