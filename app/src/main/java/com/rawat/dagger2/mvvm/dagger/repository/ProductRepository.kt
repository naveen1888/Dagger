package com.rawat.dagger2.mvvm.dagger.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rawat.dagger2.mvvm.dagger.db.FakerDB
import com.rawat.dagger2.mvvm.dagger.models.Product
import com.rawat.dagger2.mvvm.dagger.retrofit.FakerAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val fakerAPI: FakerAPI
) {

//    fun getProducts(): Flow<List<Product>> {
//        return flow { emit(fakerAPI.getProducts()) }
//    }
//
//    fun getProductsFlow(): Flow<List<Product>> {
//        return flow { emit(fakerAPI.getProductsFlow()) }
//    }
//
//    fun getProductUnit(): Flow<Unit> {
//        return flow {
//            emit(fakerAPI.getProductsUnit())
//        }
//    }

    fun getProducts() = flow {
        emit(fakerAPI.getProducts())
    }

    fun getProductsFlow() = flow {
        emit(fakerAPI.getProductsFlow())
    }

    fun getProductUnit() = flow {
        emit(fakerAPI.getProductsUnit())
    }

}