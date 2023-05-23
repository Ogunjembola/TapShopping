package com.example.tapshopping.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapshopping.data.model.DataModel
import com.example.tapshopping.data.model.GetUserData
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
    private val _userLogin: MutableLiveData<Resource<UsersResponse>> = MutableLiveData()
    val users: LiveData<Resource<UsersResponse>> get() = _user
    val userLogin: LiveData<Resource<UsersResponse>> get() = _userLogin
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    fun fetchUsers(userName: String, password: String) {
        viewModelScope.launch {
            _userLogin.value = Resource.loading()
            val loginUser = GetUserData(
                UserLoginData(
                    password = password,
                    username = userName
                )
            )
            Log.d("viewMessage", "fetchUsers: response gotten successfully")
            shoppingRepository.getUser(loginUser).collect { response ->

                if (response.isSuccess()) {
                    _userLogin.postValue(Resource.success(response.data))
                } else {
                    _userLogin.postValue(Resource.error(response.message))
                }
                //Log.d("password-user", response.)
            }

        }
    }


        fun registerUser(name: String, userName: String, email: String, password: String) {
            Resource.loading(true)
            viewModelScope.launch {
                _user.value = Resource.loading()
                val createUser = DataModel(
                    UserRegistrationData(
                        email = email,
                        name = name,
                        password = password,
                        username = userName
                    )
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