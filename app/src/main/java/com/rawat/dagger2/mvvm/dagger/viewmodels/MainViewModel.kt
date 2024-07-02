package com.rawat.dagger2.mvvm.dagger.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rawat.dagger2.mvvm.dagger.UiState
import com.rawat.dagger2.mvvm.dagger.models.Product
import com.rawat.dagger2.mvvm.dagger.repository.DatabaseRepository
import com.rawat.dagger2.mvvm.dagger.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

    private val _productLiveData = MutableLiveData<UiState<List<Product>>>(UiState.Loading)
    val productLiveData: LiveData<UiState<List<Product>>> get() = _productLiveData

    private val _uiState: MutableStateFlow<UiState<List<Product>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Product>>> get() = _uiState

    init {
        //fetchProductWithLiveData()
        //fetchProductWithStateFlow()
        //fetchProductDbWithStateFlow()
    }

    fun fetchProductWithLiveData() {
        if (_productLiveData.value == UiState.Loading) {
            viewModelScope.launch(Dispatchers.Main) {
                delay(2000)
                productRepository.getProducts()
//                    .map {
//                        val productList = mutableListOf<Product>()
//                        for (item in it) {
//                            val product = Product(
//                                item.category,
//                                item.description,
//                                item.id,
//                                item.image,
//                                item.price,
//                                item.title,
//                            )
//                            productList.add(product)
//                        }
//                        productList
//                    }
                    .flowOn(Dispatchers.IO) // above this all code will be executed on io and below on Main
                    .catch { throwable ->
                        val message: String? = if (throwable is retrofit2.HttpException) {
                            "Error message " + throwable.message + " Error code " + throwable.code()
                        } else {
                            throwable.message
                        }
                        _productLiveData.value = UiState.Error(message)
                    }.collect {
                        _productLiveData.value = UiState.Success(it) //Success case
                    }
            }
        }
    }

    fun fetchNothing() {
        if (_uiState.value == UiState.Loading) {
            viewModelScope.launch(Dispatchers.Main) {
                productRepository.getProductUnit()
                    .flowOn(Dispatchers.IO)
                    .catch { throwable ->
                        val message: String? = if (throwable is retrofit2.HttpException) {
                            "Error message " + throwable.message + " Error code " + throwable.code()
                        } else {
                            throwable.message
                        }
                        Log.e("TAG", message.toString())
                    }.collect {
                        Log.e("TAG", "Success")

                    }
            }
        }
    }

    fun fetchProductWithStateFlow() {
        if (_uiState.value == UiState.Loading) {
            viewModelScope.launch(Dispatchers.Main) {
                productRepository.getProductsFlow()
                    .map {
                        val productList = mutableListOf<Product>()
                        for (item in it) {
                            val product = Product(
                                item.category,
                                item.description,
                                item.id,
                                item.image,
                                item.price,
                                item.title
                            )
                            productList.add(product)
                        }
                        productList
                    }
                    .flowOn(Dispatchers.IO) // above this all code will be executed on io and below on Main
                    .catch { throwable ->
                        val message: String? = if (throwable is retrofit2.HttpException) {
                            "Error message " + throwable.message + " Error code " + throwable.code()
                        } else {
                            throwable.message
                        }
                        _uiState.value = UiState.Error(message)
                    }.collect {
                        _uiState.value = UiState.Success(it) //Success case
                    }
            }
        }
    }

    fun fetchProductDbWithStateFlow() {
        if (_uiState.value == UiState.Loading) {
            viewModelScope.launch(Dispatchers.Main) {
                databaseRepository.getAllUser()
                    .flatMapConcat { productFromDB ->
                        if (productFromDB.isEmpty()) {
                            return@flatMapConcat productRepository.getProductsFlow()
                                .map { apiList ->
                                    val productList = mutableListOf<Product>()
                                    for (item in apiList) {
                                        val product = Product(
                                            item.category,
                                            item.description,
                                            item.id,
                                            item.image,
                                            item.price,
                                            item.title
                                        )
                                        productList.add(product)
                                    }
                                    productList
                                }.flatMapConcat { productToInsertInDb ->
                                    databaseRepository.insertAll(productToInsertInDb)
                                        .flatMapConcat {
                                            flow {
                                                emit(productToInsertInDb)
                                            }
                                        }
                                }
                        } else {
                            return@flatMapConcat flow {
                                emit(productFromDB)
                            }
                        }
                    }
                    .flowOn(Dispatchers.IO) // above this all code will be executed on io and below on Main
                    .catch {
                        _uiState.value = UiState.Error(it) // Error case
                    }.collect {
                        _uiState.value = UiState.Success(it) //Success case
                    }
            }
        }
    }

}
