package com.example.tapshopping.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.data.model.AuthResponse
import com.example.tapshopping.data.model.CreateProduct
import com.example.tapshopping.data.model.CreateProductData
import com.example.tapshopping.data.model.ProductResponse
import com.example.tapshopping.domain.ShoppingProductRepository
import com.example.tapshopping.utillz.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val shoppingProductRepository: ShoppingProductRepository,
    dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _createProduct: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val createProduct: LiveData<Resource<AuthResponse>>
        get() = _createProduct

    private val _products: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    val loading = MutableLiveData<Boolean>()
    val products: LiveData<Resource<ProductResponse>> get() = _products

    private val token = dataStoreManager.token
    private val bearerToken = "Bearer ".plus(token)

    fun createProduct(
        name: String,
        description: String,
        price: Int,
        discount: Int,
        productImage: List<String>,
        categoryId: String,
        quantity: String,
        inStock: Boolean
    ) {
        viewModelScope.launch {
            _createProduct.postValue(Resource.loading())

            val createProductData = CreateProduct(
                CreateProductData(
                    categoryId = categoryId,
                    productName = name,
                    productDescription = description,
                    productPrice = price,
                    discountedPrice = discount,
                    productImages = productImage,
                    quantity = quantity,
                    inStock = inStock
                )
            )
            shoppingProductRepository.createProduct(createProduct = createProductData, token = bearerToken).collect{response ->
                _createProduct.postValue(response)
            }
        }

    }
    fun fetchProducts() {
        viewModelScope.launch {
            _products.value = Resource.loading()
            shoppingProductRepository.getProduct(bearerToken).collect {
                _products.postValue(it)

            }

        }
    }
}
