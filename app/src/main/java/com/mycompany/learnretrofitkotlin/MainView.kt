package com.mycompany.learnretrofitkotlin

interface MainView {

    fun showLoading()
    fun hideLoading()
    fun onGetResult(entityList: List<Entity?>?)
    fun onErrorLoading(message: String?)

}