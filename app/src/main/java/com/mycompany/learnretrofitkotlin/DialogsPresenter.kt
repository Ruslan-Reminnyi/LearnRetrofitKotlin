package com.mycompany.learnretrofitkotlin

import android.widget.Toast
import com.google.gson.GsonBuilder
import com.mycompany.learnretrofitkotlin.Dialogs.AddDialog
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DialogsPresenter(_view: DialogsInterface?) {

    private var view = _view

    companion object {
        private const val BASE_URL = ""
    }

    fun sendDataToServer(name: String, age: Int, position: String) {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        val apiInterface: ApiInterface = retrofit.create<ApiInterface>(ApiInterface::class.java)
        val call: Call<Entity?> = apiInterface.sendData(name, age, position)
        call.enqueue(object : Callback<Entity?> {
            override fun onResponse(
                call: Call<Entity?>,
                response: Response<Entity?>
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    val success: Boolean? = response.body()!!.getSuccess()
                    if (success!!) {
                        view?.showToast(response.body()!!.getMessage())
                    } else {
                        view?.showToast(response.body()!!.getMessage())
                    }
                }
            }

            override fun onFailure(
                call: Call<Entity?>,
                t: Throwable
            ) {
                println("onFailure " + t.localizedMessage)
                view?.showToast(t.localizedMessage)
            }
        })

    }

    fun editDataOnServer(id: Int, name: String, age: Int, position: String) {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        val apiInterface: ApiInterface = retrofit.create<ApiInterface>(ApiInterface::class.java)
        val call: Call<Entity?> = apiInterface.updateData(id, name, age, position)
        call.enqueue(object : Callback<Entity?> {
            override fun onResponse(
                call: Call<Entity?>,
                response: Response<Entity?>
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    val success: Boolean? = response.body()!!.getSuccess()
                    if (success!!) {
                        view?.showToast(response.body()!!.getMessage())
                    } else {
                        view?.showToast(response.body()!!.getMessage())
                    }
                }
            }

            override fun onFailure(call: Call<Entity?>, t: Throwable) {
                println("onFailure " + t.localizedMessage)
                view?.showToast(t.localizedMessage)
            }
        })
    }

    fun deleteContact(selectId : Int) {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        val apiInterface: ApiInterface = retrofit.create<ApiInterface>(ApiInterface::class.java)
        val call: Call<Entity?> = apiInterface.deleteData(selectId)
        call.enqueue(object : Callback<Entity?> {
            override fun onResponse(
                call: Call<Entity?>,
                response: Response<Entity?>
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    val success: Boolean? = response.body()!!.getSuccess()
                    if (success!!) {
                        view?.showToast(response.body()!!.getMessage())
                    } else {
                        view?.showToast(response.body()!!.getMessage())
                    }
                }
            }

            override fun onFailure(call: Call<Entity?>, t: Throwable) {
                println("onFailure " + t.localizedMessage)
                view?.showToast(t.localizedMessage)
            }
        })
    }

}