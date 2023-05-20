package com.example.tapshopping.ui.viewModel

import android.app.Application
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapshopping.data.model.DataModel
import com.example.tapshopping.data.model.UserLoginData
import com.example.tapshopping.data.model.UserRegistrationData
import com.example.tapshopping.data.model.UsersResponse
import com.example.tapshopping.domain.ShoppingRepository
import com.example.tapshopping.utillz.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
     private val shoppingRepository: ShoppingRepository
) : ViewModel() {

    private val _user: MutableLiveData<Resource<UsersResponse>> = MutableLiveData()
    val users: LiveData<Resource<UsersResponse>> get() = _user
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    fun fetchUsers(userLogin: UserLoginData) {
        viewModelScope.launch {
            _user.value = Resource.loading()
            val result = shoppingRepository.getUser(userLogin)
            result.collect { values ->
                if (values.isSuccess()) {

                    _user.value = Resource.success(values.data)
                    Resource.loading(true)
                    Resource.error(" Unable to login user to the server", false)

                } else {
                    _user.value = Resource.error(message = values.message)
                    Resource.loading(true)
                    Resource.error(" Unable to login user to the server", false)
                }
            }
        }
    }


    fun registerUser(fullName: String, userName: String, email: String, password: String) {
        Resource.loading(true)
        viewModelScope.launch {
            _user.value = Resource.loading()
            val createUser =  UserRegistrationData(
                    email = email,
                    name = fullName,
                    password = password,
                    username = userName
                )

            try {
                shoppingRepository.createUser(createUser)
                    .collect { response ->
                        _user.postValue(response)
                    }
            } catch (e: Throwable) {
                _errorMessage.postValue(e.localizedMessage)
            }

        }
    }


}