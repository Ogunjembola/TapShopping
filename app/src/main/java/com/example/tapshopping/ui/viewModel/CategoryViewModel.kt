package com.example.tapshopping.ui.viewModel

import android.util.Log
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

    private val getToken = dataStoreManager.token
    private val token = "Bearer ".plus(getToken)

    private val _createCategory: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val createCategory: LiveData<Resource<AuthResponse>>
        get() = _createCategory

    private val _getCategories: MutableLiveData<Resource<Category>> = MutableLiveData()
    val getCategories: LiveData<Resource<Category>>
        get() = _getCategories

    private val _deleteCategory: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val deleteCategory: LiveData<Resource<AuthResponse>>
        get() = _deleteCategory

    private val _updateCategory: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val updateCategory: LiveData<Resource<AuthResponse>>
        get() = _updateCategory

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

    fun getCategories() {
        viewModelScope.launch {
            _getCategories.postValue(Resource.loading())
            shoppingCategoryRepository.getCategories().collect { response ->
                _getCategories.postValue(response)

                Log.d("CategoryViewModel", "token: $token ")
            }
        }
    }

    fun deleteCategory(categoryId: String) {
        viewModelScope.launch {
            _deleteCategory.postValue(Resource.loading())
            Log.d("CategoryViewModel", "deleteCategory: categoryId = $categoryId ")
            shoppingCategoryRepository.deleteCategory(token = token, categoryId = categoryId)
                .collect { result ->
                    _deleteCategory.postValue(result)
                }
        }
    }

    fun updateCategory(categoryId: String, updatedCatName: String, updatedCatDescription: String) {
        viewModelScope.launch {
            _updateCategory.postValue(Resource.loading())
            val category = CreateCategory(
                CreateCategoryData(
                    name = updatedCatName,
                    description = updatedCatDescription
                )
            )
            shoppingCategoryRepository.updateCategory(
                token = token,
                categoryId = categoryId,
                category = category
            )
                .collect { response ->
                    _updateCategory.postValue(response)
                }

        }
    }
}