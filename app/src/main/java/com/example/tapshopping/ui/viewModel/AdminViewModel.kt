package com.example.tapshopping.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tapshopping.data.model.AdminAuthResponse
import com.example.tapshopping.data.model.CreateAdmin
import com.example.tapshopping.data.model.CreateAdminData
import com.example.tapshopping.domain.ShoppingRepository
import com.example.tapshopping.utillz.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(private val repository: ShoppingRepository) : ViewModel() {

    private val _createAdmin: MutableLiveData<Resource<AdminAuthResponse>> = MutableLiveData()
    val createAdmin: LiveData<Resource<AdminAuthResponse>>
        get() = _createAdmin

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorLiveData: LiveData<String>
    get() = _errorMessage

    fun createAdminAccount(fullName: String, userName: String, email: String, password: String) {

        viewModelScope.launch {
            val createAdminData = CreateAdmin(createAdminData = CreateAdminData(
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