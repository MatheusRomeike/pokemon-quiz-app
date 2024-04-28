package com.example.myrecyclerviewapplication.model.user

import javax.inject.Inject
import javax.inject.Singleton

class UserRepository @Inject constructor(var dao: UserDao) {
    public var user: User? = null

    fun insert(user: User){
        dao.insert(user)
        this.user = user
    }
    fun getById(id: Int){
        user = dao.getById(id)
    }
    fun getUserByNameAndPassword(name: String, password: String){
        user = dao.getUserByNameAndPassword(name, password)
    }

    fun getUserByName(name: String){
        user = dao.getUserByName(name)

    }
}