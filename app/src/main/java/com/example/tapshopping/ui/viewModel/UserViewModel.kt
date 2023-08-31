package com.example.tapshopping.ui.viewModel

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapshopping.data.local.DataStoreManager
import com.example.tapshopping.data.model.*
import com.example.tapshopping.domain.ShoppingRepository
import com.example.tapshopping.utillz.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val shoppingRepository: ShoppingRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _user: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    private val _userLogin: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val users: LiveData<Resource<AuthResponse>> get() = _user
    val userLogin: LiveData<Resource<AuthResponse>> get() = _userLogin
    private val _errorMessage: MutableLiveData<String> = MutableLiveData()

    private val _userData: MutableLiveData<Resource<GetUserResponse>> = MutableLiveData()
    val userData: LiveData<Resource<GetUserResponse>>
    get() = _userData

    fun getUserData(){
        viewModelScope.launch {
        _userData.postValue(Resource.loading())

            val token = dataStoreManager.token

            Log.d("UserViewModel", "getUserDataToken:$token ")

            shoppingRepository.getUserData(token ="Bearer ".plus(token)).collect{getUserData ->
                _userData.postValue(getUserData)
                if (getUserData.isSuccess()){
                    dataStoreManager.userName = getUserData.data!!.userData.data.user.username
                    dataStoreManager.fullName = getUserData.data.userData.data.user.name
                    dataStoreManager.email = getUserData.data.userData.data.user.email
                    dataStoreManager.userId = getUserData.data.userData.data.user.userId
                    dataStoreManager.userType = "User"
                    Log.d("User ID", "getUser ID: ${dataStoreManager.userId}")
                }else if (getUserData.isError()){
                    Log.d("UserViewModel", "getUserData: ${getUserData.message}")
                }

            }
        }
    }

    fun fetchUsers(userName: String, password: String) {
        viewModelScope.launch {
            _userLogin.value = Resource.loading()
            val loginUser = Login(
                LoginData(
                    password = password,
                    username = userName
                )
            )
            Log.d("viewMessage", "fetchUsers: response gotten successfully")
            shoppingRepository.getUser(loginUser).collect { response ->

                if (response.isSuccess()) {
                    _userLogin.postValue(Resource.success(response.data))
//                    dataStoreManager.userName = userName
                    dataStoreManager.token = response.data!!.responseData.data.token
                } else {
                    _userLogin.postValue(Resource.error(response.message))
                }
            }

        }
    }


    fun registerUser(name: String, userName: String, email: String, password: String) {
        Resource.loading(true)
        viewModelScope.launch {
            _user.value = Resource.loading()
            val createUser = Registration(
                RegisterData(
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