package com.mycompany.learnretrofitkotlin

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @POST("save.php")
    fun sendData(
        @Field("name") name: String?,
        @Field("age") age: Int,
        @Field("position") position: String?
    ): Call<Entity?>

    @POST("save_object.php")
    fun sendEntity(@Body entity: Entity?): Call<Entity?>?

    @GET("get_data.php")
    fun getData(): Call<List<Entity?>?>?

    @FormUrlEncoded
    @POST("update.php")
    fun updateData(
        @Field("id") id: Int,
        @Field("name") name: String?,
        @Field("age") age: Int,
        @Field("position") position: String?
    ): Call<Entity?>

    @FormUrlEncoded
    @POST("delete.php")
    fun deleteData(@Field("id") id: Int): Call<Entity?>

}