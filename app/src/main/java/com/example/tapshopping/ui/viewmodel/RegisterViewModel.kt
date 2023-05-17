package com.example.tapshopping.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tapshopping.data.model.DataModel
import com.example.tapshopping.data.model.UserRegistrationData
import com.example.tapshopping.data.model.UsersResponse
import com.example.tapshopping.domain.ShoppingRepository
import com.example.tapshopping.utillz.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    application: Application, private val shoppingRepository: ShoppingRepository
) : BaseViewModel(application) {

    private val _user = MutableLiveData<Resource<UsersResponse>>()
    val users: LiveData<Resource<UsersResponse>> = _user

    fun registerUser(userRegistration: UserRegistrationData) {
       Resource.loading(true)
        viewModelScope.launch {
            _user.value = Resource.loading()
            val result = shoppingRepository.createUser(userRegistration)

            result.collect { values ->
                if (values.isSuccess()) {
                    _user.value = Resource.success(values.data)
                } else {
                    _user.value = Resource.error(message = values.message)
                }
            }


        }
    }

    }