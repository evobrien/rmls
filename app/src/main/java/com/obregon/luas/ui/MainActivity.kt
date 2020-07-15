package com.obregon.luas.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.obregon.luas.R
import com.obregon.luas.ui.HomeScreen.HomeScreenFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setInitialLayout()
    }

    private fun setInitialLayout(){
        this.supportFragmentManager.beginTransaction()
            .replace(
                R.id.content,
                HomeScreenFragment(),
                HomeScreenFragment::class.simpleName)
            .commit()
    }
}