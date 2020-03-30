package com.mycompany.learnretrofitkotlin

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainPresenter(_view: MainView?) {

    private var view = _view
    private val BASE_URL = ""

    fun getData() {    // get data from server
        view?.showLoading()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiInterface: ApiInterface = retrofit.create<ApiInterface>(ApiInterface::class.java)
        val call: Call<List<Entity?>?>? = apiInterface.getData()
        call?.enqueue(object : Callback<List<Entity?>?> {
            override fun onResponse(
                call: Call<List<Entity?>?>,
                response: Response<List<Entity?>?>
            ) {
                view?.hideLoading()
                if (response.isSuccessful && response.body() != null) {
                    view?.onGetResult(response.body())
                }
            }

            override fun onFailure(
                call: Call<List<Entity?>?>,
                t: Throwable
            ) {
                view?.hideLoading()
                view?.onErrorLoading(t.localizedMessage)
            }
        })
    }

}