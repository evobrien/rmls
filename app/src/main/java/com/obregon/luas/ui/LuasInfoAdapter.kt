package com.obregon.luas.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.obregon.luas.R


class TramDataAdapter(private val trams:List<Tram>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            0 -> {
                 ListHeaderViewHolder(
                     inflater.inflate(R.layout.header_cell, parent, false))
            }
            else -> {
                ListViewHolder(
                    inflater.inflate(R.layout.list_cell, parent, false))
            }
        }

    }

    override fun getItemCount(): Int {
        return trams.size-1
    }

    override fun onBindViewHolder(viewHolder:RecyclerView.ViewHolder, position: Int) {
        val tram= trams[position]
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

        fun bind(tram:Tram){
            tvDestination.text=tram.station
            tvDueTime.text=tram.dueMins
        }
    }

    class ListHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvDestinationTitle : TextView = itemView.findViewById(R.id.destination)
        private var tvDueTimeTitle: TextView = itemView.findViewById(R.id.dueTime)

        fun bind(tram:Tram){
            tvDestinationTitle.text=tram.station
            tvDueTimeTitle.text=tram.dueMins
        }
    }

}