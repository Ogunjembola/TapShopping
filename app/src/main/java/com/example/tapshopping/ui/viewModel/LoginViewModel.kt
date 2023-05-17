package com.example.tapshopping.ui.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tapshopping.data.model.UserLoginData
import com.example.tapshopping.data.model.UsersResponse
import com.example.tapshopping.domain.ShoppingRepository
import com.example.tapshopping.ui.viewmodel.BaseViewModel
import com.example.tapshopping.utillz.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    application: Application, private val shoppingRepository: ShoppingRepository
) : BaseViewModel(application) {

    private val _user = MutableLiveData<Resource<UsersResponse>>()
    val users: LiveData<Resource<UsersResponse>> = _user
    fun fetchUsers(userLogin: UserLoginData) {
        viewModelScope.launch {
            _user.value = Resource.loading()
            val result = shoppingRepository.getUser(userLogin)
            result.collect { values ->
                if (values.isSuccess()) {

                    _user.value = Resource.success(values.data)
                    Resource.loading(true)
                    Resource.error(" Unable to login user to the server",false)

                } else {
                    _user.value = Resource.error(message = values.message)
                    Resource.loading(true)
                    Resource.error(" Unable to login user to the server",false)
                }
            }
        }
    }
}