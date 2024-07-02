package com.rawat.dagger2.mvvm.dagger.ui.home

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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _productLiveData = MutableLiveData<UiState<List<Product>>>(UiState.Loading)
    val productLiveData: LiveData<UiState<List<Product>>> get() = _productLiveData

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
}