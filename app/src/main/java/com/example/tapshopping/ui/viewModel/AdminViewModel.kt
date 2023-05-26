package com.example.tapshopping.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapshopping.data.model.AuthResponse
import com.example.tapshopping.data.model.Registration
import com.example.tapshopping.data.model.RegisterData
import com.example.tapshopping.domain.ShoppingRepository
import com.example.tapshopping.utillz.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(private val repository: ShoppingRepository) : ViewModel() {

    private val _createAdmin: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val createAdmin: LiveData<Resource<AuthResponse>>
        get() = _createAdmin

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String>
    get() = _errorMessage

    fun createAdminAccount(fullName: String, userName: String, email: String, password: String) {

        viewModelScope.launch {
            _createAdmin.postValue(Resource.loading())
            val createAdminData = Registration(registerData = RegisterData(
                email = email,
                name = fullName,
                password = password,
                username = userName
            ))

            try {
                repository.createAdmin(createAdminData)
                    .collect{response ->
                    _createAdmin.postValue(response)
                }
            }catch (e: Throwable){
                _errorMessage.postValue(e.localizedMessage)
            }
        }
    }
}