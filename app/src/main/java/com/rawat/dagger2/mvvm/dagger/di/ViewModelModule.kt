package com.rawat.dagger2.mvvm.dagger.di

import androidx.lifecycle.ViewModel
import com.rawat.dagger2.mvvm.dagger.ui.home.HomeViewModel
import com.rawat.dagger2.mvvm.dagger.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @ClassKey(MainViewModel::class)
    @IntoMap
    abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @ClassKey(HomeViewModel::class)
    @IntoMap
    abstract fun homeViewModel(homeViewModel: HomeViewModel): ViewModel
}