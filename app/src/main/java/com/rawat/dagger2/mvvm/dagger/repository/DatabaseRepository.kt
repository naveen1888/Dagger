package com.rawat.dagger2.mvvm.dagger.repository

import com.rawat.dagger2.mvvm.dagger.db.FakerDB
import com.rawat.dagger2.mvvm.dagger.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseRepository @Inject constructor(private val fakerDB: FakerDB) {

//    fun getAllUser(): Flow<List<Product>> {
//        return flow {
//            emit(fakerDB.getFakerDAO().getProducts())
//        }
//    }
//
//    fun insertAll(products: List<Product>): Flow<Unit> {
//        return flow {
//            fakerDB.getFakerDAO().addProducts(products)
//            emit(Unit)
//        }
//    }

    fun getAllUser() = flow {
        emit(fakerDB.getFakerDAO().getProducts())
    }

    fun insertAll(products: List<Product>) = flow {
        emit(fakerDB.getFakerDAO().addProducts(products))
    }

}