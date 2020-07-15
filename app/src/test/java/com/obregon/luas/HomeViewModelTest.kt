package com.obregon.luas

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.obregon.luas.data.repository.LuasRepository
import com.obregon.luas.ui.LuasHomeViewModel
import com.obregon.luas.ui.StopCodeProvider
import com.obregon.luas.ui.StopFilter
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class HomeViewModelTest{
    @Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private var repository=mock(LuasRepository::class.java)
    private var stopCodeProvider=mock(StopCodeProvider::class.java)
    var viewModel= LuasHomeViewModel(repository,stopCodeProvider)

    @Before
    fun setup(){

    }



}