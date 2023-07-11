package com.example.tapshopping.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.data.model.AuthResponse
import com.example.tapshopping.data.model.Product
import com.example.tapshopping.data.model.ProductResponse
import com.example.tapshopping.domain.ProductRepository
import com.example.tapshopping.utillz.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository, private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _products: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    val loading = MutableLiveData<Boolean>()
    val products: LiveData<Resource<ProductResponse>> get() = _products

    fun fetchProducts() {
        viewModelScope.launch {
            _products.value = Resource.loading()
            val token = dataStoreManager.token
            productRepository.getProduct(token = "Bearer ".plus(token)).collect {
                _products.postValue(it)

            }

        }
    }
}
