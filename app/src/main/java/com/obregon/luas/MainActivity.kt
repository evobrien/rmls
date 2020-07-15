package com.obregon.luas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.obregon.luas.ui.LuasHomeFragment
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
            .replace(R.id.content, LuasHomeFragment(),LuasHomeFragment::class.simpleName)
            .addToBackStack(LuasHomeFragment::class.simpleName)
            .commit()
    }
}