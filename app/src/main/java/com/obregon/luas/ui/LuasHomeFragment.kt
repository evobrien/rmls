package com.obregon.luas.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.obregon.luas.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_layout.*
import timber.log.Timber


@AndroidEntryPoint
class LuasHomeFragment : Fragment() {

    private val viewModel:LuasHomeViewModel  by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showProgress()
        viewModel.uiData.observeForever {layout(it)}
        viewModel.errorData.observeForever{showError(it)}
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.refresh->reload()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun reload(){
        showProgress()
        viewModel.loadData()
    }

    private fun showError(error:String){
        hideProgress()
        Toast.makeText(this.context,error,Toast.LENGTH_LONG).show()
    }

    private lateinit var tramDataAdapter:TramDataAdapter

    private fun layout(uiData: UiData){

        Timber.d(uiData.toString())
        tv_stop.text=uiData.stationName
        tv_direction.text=uiData.direction
        tv_message.text=uiData.statusMessage
        tv_last_updated.text=uiData.lastUpdated

        header_card.visibility=View.VISIBLE

        tram_list.apply{
            layoutManager= LinearLayoutManager(this.context)
            tramDataAdapter= TramDataAdapter(trams = uiData.trams)
            adapter=tramDataAdapter
        }

        tram_card.visibility=View.VISIBLE
        hideProgress()
    }

    private fun showProgress(){
        progressBar.visibility=View.VISIBLE
        ViewCompat.setTranslationZ(progressBar, 2F)
    }

    private fun hideProgress(){
       progressBar.visibility=View.INVISIBLE
    }
}