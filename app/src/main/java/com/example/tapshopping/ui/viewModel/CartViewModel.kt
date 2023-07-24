package com.example.tapshopping.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.data.model.CartData
import com.example.tapshopping.data.model.CartDeleteResponse
import com.example.tapshopping.data.model.CartProduct
import com.example.tapshopping.data.model.CartProductData
import com.example.tapshopping.data.model.CartResponse
import com.example.tapshopping.data.model.CreateCart
import com.example.tapshopping.data.model.CreateCartData
import com.example.tapshopping.domain.CartRepository
import com.example.tapshopping.utillz.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    private val _createCart: MutableLiveData<Resource<CartResponse>> = MutableLiveData()
    val createCart: LiveData<Resource<CartResponse>>
        get() = _createCart
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()

    private val _cartItems: MutableLiveData<Resource<CartResponse>> = MutableLiveData()
    val cartItems: LiveData<Resource<CartResponse>>
        get() = _cartItems


    private val _deleteCart: MutableLiveData<Resource<CartDeleteResponse>> = MutableLiveData()
    val deleteCart: LiveData<Resource<CartDeleteResponse>>
        get() = _deleteCart


    fun createCartList(
        userID: String? = null,
        basePrice: Int? = null,
        productID: String,
        quantity: Int,
        totalPrice: Int? = null
    ) {
        viewModelScope.launch {
            _createCart.postValue(Resource.loading())
            val token = dataStoreManager.token
            val products: List<CartProductData> = listOf(
                CartProductData(

                    productID = productID,
                    quantity = quantity,

                )
            )
            val createCart =CreateCart(CreateCartData(products =products,
                userID =userID ?: String()
            ))

            try {
                repository.createCart(token = "Bearer ".plus(token), createCart = createCart)
                    .collect { response ->
                        _createCart.postValue(response)
                    }
            } catch (e: Throwable) {
                _errorMessage.postValue(e.localizedMessage)
            }
        }
    }

    fun getCartItems() {
        viewModelScope.launch {
            _cartItems.postValue(Resource.loading())
            val token = dataStoreManager.token // Assuming a method to retrieve token from DataStore
            repository.getCart("Bearer $token").collect { response ->
                _cartItems.postValue(response)
            }
        }
    }
fun deleteCartitem(cartItemId:String? = null, userID: String? = null, quantity: Int? = null){
    viewModelScope.launch {
        _deleteCart.postValue(Resource.loading())
        val token = dataStoreManager.token
//        val deleteCart = List<Product>= listOf(
//            Product(
//                productID= cartItemId,
//                quantity = quantity
//
//            )
//        )
//        val cartDeleteData = Data(
//            userID = userID,
//            products = pro
//
//        )
    }
}
}
//    fun deleteCartItem(
//        cartItemId: String? = null,
//        userID?: String? = null , quantity: Int? = null) {
//
//    }
//    }
//}   viewModelScope.launch {
//    _deleteCart.postValue(Resource.loading())
//
//    val token = dataStoreManager.token // Assuming a method to retrieve token from DataStore
//    val deleteCart =
//        CartDelete(CartDelete.Data(listOf(CartDelete.Data.Product(cartItemId, 1)), userID = userID))
//    repository.deleteCart(deleteCart, token).collect { response ->
//        _deleteCart.postValue(response)
//    }
//
//}