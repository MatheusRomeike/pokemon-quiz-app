package com.example.myrecyclerviewapplication.model.user_score

import javax.inject.Inject

class UserScoreRepository @Inject constructor(var dao: UserScoreDao) {
    public var userScore: UserScore? = null

    fun insert(userPokemon: UserScore) {
        dao.insert(userPokemon)
    }

    fun getByUserId(userId: Int) {
        userScore = dao.getByUserId(userId)
    }

    fun update(userId: Int, score: Int) {
        dao.update(userId, score)
    }
}