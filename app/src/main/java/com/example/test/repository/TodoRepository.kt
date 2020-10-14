package com.example.test.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test.model.MainTodo
import com.example.test.model.SubTodo
import com.example.test.model.TodoResponse
import com.example.test.network.MyApi
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TodoRepository {
    fun subTodoPatch(auth_token: String, id: Int, item_id: Int, done: Boolean):LiveData<TodoResponse> {
        var updateResponse = MutableLiveData<TodoResponse>()
        var todoResponse = TodoResponse(null, "")
        MyApi().subTodoPatch(auth_token, id, item_id, done).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                updateResponse.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    todoResponse.message = "Successfully patched. \nPlease reload this page"
                    var bodyString = response.body()?.string().toString()
                    var mainTodo = Gson().fromJson(bodyString, MainTodo::class.java)
                    todoResponse.mainTodo = mainTodo
                } else {
                    todoResponse.message = response.errorBody()?.string().toString()
                }
                updateResponse.postValue(todoResponse)
            }
        })
        return updateResponse
    }
    fun subTodoUpdate(auth_token: String, id: Int, item_id: Int, name: String, done: Boolean):LiveData<TodoResponse> {
        var updateResponse = MutableLiveData<TodoResponse>()
        var todoResponse = TodoResponse(null, "")
        MyApi().subTodoUpdate(auth_token, id, item_id, name, done).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                updateResponse.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    todoResponse.message = "Successfully updated. \nPlease reload this page"
                    var bodyString = response.body()?.string().toString()
                    var mainTodo = Gson().fromJson(bodyString, MainTodo::class.java)
                    todoResponse.mainTodo = mainTodo
                } else {
                    todoResponse.message = response.errorBody()?.string().toString()
                }
                updateResponse.postValue(todoResponse)
            }
        })
        return updateResponse
    }
    fun subTodoCreate(auth_token: String, id: Int, name: String, done: Boolean):LiveData<TodoResponse> {
        var createResponse = MutableLiveData<TodoResponse>()
        var todoResponse = TodoResponse(null, "")
        MyApi().subTodoCreate(auth_token, id, name, done).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                createResponse.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    todoResponse.message = "Successfully created"
                    var bodyString = response.body()?.string().toString()
                    var mainTodo = Gson().fromJson(bodyString, MainTodo::class.java)
                    todoResponse.mainTodo = mainTodo
                } else {
                    todoResponse.message = response.errorBody()?.string().toString()
                }
                createResponse.postValue(todoResponse)
            }
        })
        return createResponse
    }
    fun todoDelete(auth_token: String, id: Int):LiveData<String> {
        var deleteResponse = MutableLiveData<String>()
        MyApi().todoDelete(auth_token, id).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                deleteResponse.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    deleteResponse.value = "Successfully deleted"
                } else {
                    deleteResponse.value = response.errorBody()?.string().toString()
                }
            }
        })
        return deleteResponse
    }

    fun mainTodoCreate(auth_token: String, title: String):LiveData<String> {
        var createResponse = MutableLiveData<String>()
        MyApi().mainTodoCreate(auth_token, title).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                createResponse.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    createResponse.value = "Successfully created"
//                    result.message = response.body()?.string().toString()
//                    var mainTodo = Gson().fromJson(result.message, MainTodo::class.java)
//                    result.mainTodo = mainTodo
                } else {
                    createResponse.value = response.errorBody()?.string().toString()
                }
            }
        })
        return createResponse
    }
    fun todoListGet(auth_token: String) : MutableLiveData<List<MainTodo>> {
        val todoGetResponse = MutableLiveData<List<MainTodo>>()
        var todoList:MutableList<MainTodo> = ArrayList()
        MyApi().todoListGet(auth_token).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                todoGetResponse.postValue(null)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                if (response.isSuccessful) {
                    var body = response.body()?.string()
                    val jsonArray = JSONArray(body)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        var mainTodo = Gson().fromJson(jsonObject.toString(), MainTodo::class.java)
                        todoList.add(mainTodo)
                    }
                }
                todoGetResponse.postValue(todoList)
            }
        })

        return todoGetResponse
    }

}