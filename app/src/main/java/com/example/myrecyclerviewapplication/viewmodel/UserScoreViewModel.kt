package com.example.myrecyclerviewapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrecyclerviewapplication.model.user_score.UserScore
import com.example.myrecyclerviewapplication.model.user_score.UserScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserScoreViewModel @Inject constructor(private val userScoreRepository: UserScoreRepository)
    : ViewModel()  {
    val userScoreData = MutableLiveData<UserScore>()

    fun insert(userPokemon: UserScore) {
        userScoreRepository.insert(userPokemon)
        userScoreData.value = userScoreRepository.userScore
    }

    fun getByUserId(userId: Int) {
        userScoreRepository.getByUserId(userId)
        userScoreData.value = userScoreRepository.userScore
    }

    fun update(userId: Int, score: Int) {
        userScoreRepository.update(userId, score)
        userScoreData.value = userScoreRepository.userScore
    }

    fun refresh(){
        userScoreData.value = userScoreRepository.userScore
    }
}
