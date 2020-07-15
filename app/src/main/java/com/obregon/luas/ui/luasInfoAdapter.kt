package com.obregon.luas.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.obregon.luas.R


class TramDataAdapter(private val trams:List<Tram>):RecyclerView.Adapter<TramDataAdapter.ListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_cell, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trams.size
    }

    override fun onBindViewHolder(holder:ListViewHolder, position: Int) {
        val tram= trams[position]
        holder.bind(tram)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var tvDestination : TextView = itemView.findViewById(R.id.destination)
        private var tvDueTime: TextView = itemView.findViewById(R.id.dueTime)

        fun bind(tram:Tram){
            tvDestination.text=tram.station
            tvDueTime.text=tram.dueMins
        }
    }

}