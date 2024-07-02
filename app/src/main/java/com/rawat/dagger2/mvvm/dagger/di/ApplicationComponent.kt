package com.rawat.dagger2.mvvm.dagger.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.rawat.dagger2.mvvm.dagger.FakerActivity
import com.rawat.dagger2.mvvm.dagger.FakerApplication
import com.rawat.dagger2.mvvm.dagger.ui.home.HomeFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun getMap(): Map<Class<*>, ViewModel>

    fun inject(fakerActivity: FakerActivity)

    fun inject(homeFragment: HomeFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}