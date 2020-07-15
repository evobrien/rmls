package com.obregon.luas.ui.HomeScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.obregon.luas.R


class TramDataAdapter(val tramData:List<TramData>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            0 -> {
                ListHeaderViewHolder(
                    inflater.inflate(R.layout.header_cell, parent, false)
                )
            }
            else -> {
                ListViewHolder(
                    inflater.inflate(R.layout.list_cell, parent, false)
                )
            }
        }

    }

    override fun getItemCount(): Int {
        return tramData.size
    }

    override fun onBindViewHolder(viewHolder:RecyclerView.ViewHolder, position: Int) {
        val tram= tramData[position]
        when(position){
            0-> (viewHolder as ListHeaderViewHolder).bind(tram)
            else ->(viewHolder as ListViewHolder).bind(tram)
        }
    }

    override fun getItemViewType(position: Int):Int{
        return when(position){
            0 -> RowType.TYPE_TITLE.type
            else -> RowType.TYPE_ROW.type
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvDestination : TextView = itemView.findViewById(R.id.destination)
        private var tvDueTime: TextView = itemView.findViewById(R.id.dueTime)

        fun bind(tramData: TramData){
            tvDestination.text=tramData.station
            tvDueTime.text=tramData.dueMins
        }
    }

    class ListHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvDestinationTitle : TextView = itemView.findViewById(R.id.destination)
        private var tvDueTimeTitle: TextView = itemView.findViewById(R.id.dueTime)

        fun bind(tramData: TramData){
            tvDestinationTitle.text=tramData.station
            tvDueTimeTitle.text=tramData.dueMins
        }
    }

}