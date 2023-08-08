package com.example.tapshopping.ui.viewModel

import android.net.Uri
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

    private val _imageUri1: MutableLiveData<Uri?> = MutableLiveData()
    val imageUri1: MutableLiveData<Uri?> = _imageUri1

    private val _imageUri2: MutableLiveData<Uri?> = MutableLiveData()
    val imageUri2: LiveData<Uri?> = _imageUri2

    private val _imageUri3: MutableLiveData<Uri?> = MutableLiveData()
    val imageUri3: LiveData<Uri?> = _imageUri3

    private val _imageUri4: MutableLiveData<Uri?> = MutableLiveData()
    val imageUri4: LiveData<Uri?> = _imageUri4

    fun setImageUri1(imageUri: Uri?){
        _imageUri1.postValue(imageUri)
    }

    fun setImageUri2(imageUri: Uri?){
        _imageUri2.postValue(imageUri)
    }

    fun setImageUri3(imageUri: Uri?){
        _imageUri3.postValue(imageUri)
    }

    fun setImageUri4(imageUri: Uri?){
        _imageUri4.postValue(imageUri)
    }

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
