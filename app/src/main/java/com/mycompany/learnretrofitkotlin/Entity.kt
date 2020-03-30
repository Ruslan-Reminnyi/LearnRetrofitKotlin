package com.mycompany.learnretrofitkotlin

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Entity {

    @Expose
    @SerializedName("id")
    private var id = 0

    @Expose
    @SerializedName("name")
    private var name: String? = null

    @Expose
    @SerializedName("age")
    private var age = 0

    @Expose
    @SerializedName("position")
    private val position: String? = null

    @Expose
    @SerializedName("success")
    private val success: Boolean? = null

    @Expose
    @SerializedName("message")
    private val message: String? = null

    fun getId(): Int = id

    fun getName(): String? = name

    fun getAge(): Int = age

    fun getPosition(): String? = position

    fun getSuccess(): Boolean? = success

    fun getMessage(): String? = message

}