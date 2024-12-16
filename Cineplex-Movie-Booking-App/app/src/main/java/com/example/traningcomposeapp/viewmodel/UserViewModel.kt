package com.example.traningcomposeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.traningcomposeapp.data.entities.UserEntity
import com.example.traningcomposeapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userInserted = MutableLiveData<Int?>()
    val userInserted: LiveData<Int?> get() = _userInserted

    fun insertUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.insertUser(user)

            val insertedUser = userRepository.getUserByEmail(user.email)
            _userInserted.postValue(insertedUser?.id)
        }
    }

    fun getUserById(userId: Int, onResult: (UserEntity?) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUserById(userId)
            onResult(user)
        }
    }

    fun getUserByEmail(email: String, onResult: (UserEntity?) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUserByEmail(email)
            onResult(user)
        }
    }

    fun getUserByUsername(username: String, onResult: (UserEntity?) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUserByUsername(username)
            onResult(user)
        }
    }
}