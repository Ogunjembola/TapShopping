package com.example.tapshopping.ui.viewModel

import android.util.Log
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
class AdminViewModel @Inject constructor(
    private val repository: ShoppingRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _createAdmin: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val createAdmin: LiveData<Resource<AuthResponse>>
        get() = _createAdmin

    private val _loginAdmin: MutableLiveData<Resource<AuthResponse>> = MutableLiveData()
    val loginAdmin: LiveData<Resource<AuthResponse>>
        get() = _loginAdmin

    private val _adminData: MutableLiveData<Resource<GetAdminResponse>> = MutableLiveData()
    val adminData: LiveData<Resource<GetAdminResponse>>
        get() = _adminData

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String>
        get() = _errorMessage

    fun createAdminAccount(fullName: String, userName: String, email: String, password: String) {

        viewModelScope.launch {
            _createAdmin.postValue(Resource.loading())
            val createAdminData = Registration(
                registerData = RegisterData(
                    email = email,
                    name = fullName,
                    password = password,
                    username = userName
                )
            )

            try {
                repository.createAdmin(createAdminData)
                    .collect { response ->
                        _createAdmin.postValue(response)
                    }
            } catch (e: Throwable) {
                _errorMessage.postValue(e.localizedMessage)
            }
        }
    }

    fun loginAdminAccount(userName: String, password: String) {
        viewModelScope.launch {
            _loginAdmin.postValue(Resource.loading())
            val loginData = Login(
                LoginData(password = password, username = userName)
            )
            repository.loginAdmin(loginData).collect {
                _loginAdmin.postValue(it)
                if (it.isSuccess()) {
                    dataStoreManager.token = it.data!!.responseData.data.token
                }
            }
        }
    }

    fun getAdminData() {
        viewModelScope.launch {
            _adminData.postValue(Resource.loading())

            val token = dataStoreManager.token

            repository.getAdminData(token = "Bearer ".plus(token)).collect{response ->
                _adminData.postValue(response)
                if (response.isSuccess()){
                    dataStoreManager.userName = response.data!!.adminData.dataResponse.admin.username
                    dataStoreManager.setIsLoggedIn(true)
                    dataStoreManager.setIsAdmin(true)
                    dataStoreManager.userId = response.data!!.adminData.dataResponse.admin.adminId
                    dataStoreManager.email = response.data!!.adminData.dataResponse.admin.email
                    dataStoreManager.fullName = response.data!!.adminData.dataResponse.admin.fullName
                    dataStoreManager.userType = "Merchant"

                    Log.d("AdminViewModel", "getAdminData=>: ${response.data.adminData.dataResponse.admin.username} \n " +
                            "${response.data.adminData.dataResponse.admin.adminId} \n" +
                            "${response.data.adminData.dataResponse.admin.email}  \n" +
                            response.data.adminData.dataResponse.admin.fullName
                    )
                }
            }
        }
    }
}