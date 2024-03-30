package com.example.mywebapplication

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RoboHashAPI {
    @GET("{text}")
    fun getImage(
        @Path("text") text: String,
        @Query("set") set: String = "set4",
    ): Call<ResponseBody>
}