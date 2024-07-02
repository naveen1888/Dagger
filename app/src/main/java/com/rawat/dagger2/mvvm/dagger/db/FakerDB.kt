package com.rawat.dagger2.mvvm.dagger.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rawat.dagger2.mvvm.dagger.models.Product

@Database(entities = [Product::class], version = 1)
abstract class FakerDB : RoomDatabase() {

    abstract fun getFakerDAO() : FakerDAO

}