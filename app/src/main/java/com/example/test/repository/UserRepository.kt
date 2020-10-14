package com.example.test.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test.network.MyApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository {

    fun userLogin(email: String, password: String) : LiveData<String> {
        val loginResponse = MutableLiveData<String>()

        MyApi().userLogin(email, password).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                loginResponse.value = t.message
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    loginResponse.value = response.body()?.string()
                } else {
                    loginResponse.value = response.errorBody()?.string()

                }
            }
        })

        return loginResponse
    }
    fun userSignup(name: String, email: String, password: String, password_confirmation: String) : LiveData<String> {
        val signupResponse = MutableLiveData<String>()

        MyApi().userSignup(name, email, password, password_confirmation).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                signupResponse.value = t.message
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    signupResponse.value = response.body()?.string()
                } else {
                    signupResponse.value = response.errorBody()?.string()

                }
            }
        })

        return signupResponse
    }
}