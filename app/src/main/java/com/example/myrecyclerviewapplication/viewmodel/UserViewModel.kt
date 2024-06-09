package com.example.myrecyclerviewapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrecyclerviewapplication.model.user.User
import com.example.myrecyclerviewapplication.model.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository)
    : ViewModel()  {

    val userData = MutableLiveData<User?>()

    fun insert(user: User){
        userRepository.insert(user)
        userData.value = userRepository.user
    }

    fun getById(id: Int){
        userRepository.getById(id)
        userData.value = userRepository.user
    }

    fun getUserByName(name: String){
        userRepository.getUserByName(name)
        userData.value = userRepository.user
    }

    fun getUserByNameAndPassword(name: String, password: String){
        userRepository.getUserByNameAndPassword(name, password)
        userData.value = userRepository.user
    }

    fun getCurrentUser(){
        userRepository.getCurrentUser()
        userData.value = userRepository.user
    }

    fun refresh(){
        userRepository.user = userRepository.user
    }
}
