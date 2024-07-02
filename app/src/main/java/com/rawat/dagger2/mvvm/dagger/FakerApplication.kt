package com.rawat.dagger2.mvvm.dagger

import android.app.Application
import com.rawat.dagger2.mvvm.dagger.di.ApplicationComponent
import com.rawat.dagger2.mvvm.dagger.di.DaggerApplicationComponent

class FakerApplication : Application(){

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}