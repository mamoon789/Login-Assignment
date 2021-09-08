package com.example.assignment.ui.vm

import androidx.lifecycle.*
import com.example.assignment.data.User
import com.example.assignment.network.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<Response<User>>()
    var user: LiveData<Response<User>> = _user

    fun insertUser(name: String, phoneNumber: String, password: String) {
        viewModelScope.launch {
            if (name.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
                _user.postValue(Response.Error(msg = "Please fill all fields"))
            }
            else if (!phoneNumber.substring(0,2).equals("03")) {
                _user.postValue(Response.Error(msg = "Please enter valid mobile number"))
            }
            else {
                if (checkUserExist(phoneNumber)) {
                    _user.postValue(Response.Error(msg = "User with this number already exist"))
                } else {
                    _user.postValue(Response.Loading())
                    delay(1000)
                    userRepository.insertUser(User(phoneNumber, name, password))
                    _user.postValue(Response.Success())
                }
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            if (user.isLogin.equals("0")) {
                user.isLogin = "1"
                _user.postValue(Response.Success(user))
            } else {
                user.isLogin = "0"
                _user.postValue(Response.Error())
            }
            userRepository.updateUser(user)
        }
    }

    fun logInUser(phoneNumber: String, password: String) {
        viewModelScope.launch {
            if (phoneNumber.isEmpty() || password.isEmpty()) {
                _user.postValue(Response.Error(msg = "Please fill all fields"))
            }
            else if (!phoneNumber.substring(0,2).equals("03")) {
                _user.postValue(Response.Error(msg = "Please enter valid mobile number"))
            }
            else {
                _user.postValue(Response.Loading())
                delay(1000)
                val user = userRepository.logInUser(phoneNumber, password)
                if (user == null)
                    _user.postValue(Response.Error(msg = "No user found with given credentials"))
                else {
                    _user.postValue(Response.Success())
                    updateUser(user)
                }
            }
        }
    }

    fun checkUserLoggedIn() {
        viewModelScope.launch {
            val user = userRepository.checkUserLoggedIn()
            if (user == null)
                _user.postValue(Response.Error())
            else {
                _user.postValue(Response.Success(user))
            }
        }
    }

    private suspend fun checkUserExist(phoneNumber: String): Boolean {
        return userRepository.checkUserExist(phoneNumber) != null
    }

    sealed class Response<T>(
        val data: T? = null,
        val message: String? = null
    ) {
        class Loading<T> : Response<T>()
        class Error<T>(data: T? = null, msg: String? = null) : Response<T>(data, msg)
        class Success<T>(data: T? = null) : Response<T>(data)
    }
}