package com.obregon.luas.ui.HomeScreen

data class TramData(val station:String, val dueMins:String, val rowType: RowType)
data class UiData constructor(val stationName:String, val direction:String, val
                              statusMessage:String, val lastUpdated:String, val tramData:List<TramData>)

enum class RowType (val type:Int){
    TYPE_TITLE(0),
    TYPE_ROW(1)
}