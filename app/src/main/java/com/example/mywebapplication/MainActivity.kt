package com.example.mywebapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mywebapplication.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://robohash.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(RoboHashAPI::class.java)
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.button.setOnClickListener {
            getRandomImage()
        }
    }

    private fun getRandomImage() {
        val call: Call<ResponseBody> = api.getImage(generateRandomText())
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val imageUrl = response.raw().request().url().toString()
                    Picasso.get().load(imageUrl).into(binding.imageView)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }

    private fun generateRandomText(): String {
        val charPool = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..10)
            .map { charPool.random() }
            .joinToString("")
    }
}