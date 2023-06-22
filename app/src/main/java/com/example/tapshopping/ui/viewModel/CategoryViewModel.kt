package com.example.tapshopping.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.data.model.AuthResponse
import com.example.tapshopping.data.model.Category
import com.example.tapshopping.data.model.CreateCategory
import com.example.tapshopping.data.model.CreateCategoryData
import com.example.tapshopping.domain.ShoppingCategoryRepository
import com.example.tapshopping.utillz.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val shoppingCategoryRepository: ShoppingCategoryRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _createCategory: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val createCategory: LiveData<Resource<AuthResponse>>
        get() = _createCategory

    private val _getCategories: MutableLiveData<Resource<Category>> = MutableLiveData()
    val getCategories: LiveData<Resource<Category>>
        get () = _getCategories

    fun createCategory(categoryName: String, categoryDescription: String) {
        viewModelScope.launch {
            _createCategory.postValue(Resource.loading())

            val createCategoryData = CreateCategory(
                CreateCategoryData(
                    name = categoryName,
                    description = categoryDescription
                )
            )
            val token = dataStoreManager.token
            val bearerToken = "Bearer ".plus(token)

            shoppingCategoryRepository.createCategories(
                category = createCategoryData,
                token = bearerToken
            ).collect { response ->
                _createCategory.postValue(response)

            }
        }
    }

    fun getCategories(){
        viewModelScope.launch {
            _getCategories.postValue(Resource.loading())
            shoppingCategoryRepository.getCategories().collect{response ->
                _getCategories.postValue(response)
            }
        }
    }
}