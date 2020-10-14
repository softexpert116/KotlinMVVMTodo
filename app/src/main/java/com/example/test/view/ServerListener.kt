package com.example.test.ui.view

import androidx.lifecycle.LiveData
import com.example.test.model.TodoResponse

interface ServerListener {
    fun onStarted()
    fun onSuccess(response: LiveData<String>)
    fun onResponseSuccess(response: LiveData<TodoResponse>)
    fun onFailure(message:String)
}