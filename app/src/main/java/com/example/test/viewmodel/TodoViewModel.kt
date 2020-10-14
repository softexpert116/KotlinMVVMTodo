package com.example.test.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.test.data.repository.TodoRepository
import com.example.test.model.MainTodo
import com.example.test.model.SubTodo
import com.example.test.ui.view.ServerListener

class TodoViewModel : ViewModel(){
    var auth_token: String ?= null
    var serverListener: ServerListener? = null
    private var todoListLiveData = MutableLiveData<List<MainTodo>>()

    open fun todoListGet(): LiveData<List<MainTodo>>?
    {
        serverListener?.onStarted()
        if (auth_token.isNullOrEmpty())
        {
            serverListener?.onFailure("Invalid Token")
            return null
        }
        else
        {
            todoListLiveData = TodoRepository().todoListGet(auth_token!!)
            val success:MutableLiveData<String> = MutableLiveData("Loading..")
            serverListener?.onSuccess(success)
            return todoListLiveData
        }
    }

    var mainTodo = MainTodo(0, "", "", "", "", ArrayList())
    fun onCreateTodoButtonClick(view: View)
    {
        serverListener?.onStarted()
        if (auth_token.isNullOrEmpty())
        {
            serverListener?.onFailure("Invalid Token")
            return
        }
        else if (mainTodo?.title.isNullOrEmpty()) {
            serverListener?.onFailure("Invalid Title")
        }
        else
        {
            var todoResponse = TodoRepository().mainTodoCreate(auth_token!!, mainTodo.title)
            serverListener?.onSuccess(todoResponse)
        }

    }
    fun todoDelete()
    {
        serverListener?.onStarted()
        if (auth_token.isNullOrEmpty())
        {
            serverListener?.onFailure("Invalid Token")
            return
        }
        else
        {
            var todoResponse = TodoRepository().todoDelete(auth_token!!, mainTodo.id)
            serverListener?.onSuccess(todoResponse)
        }

    }
    var subTodo = SubTodo(0, "", false, 0, "", "")
    fun onCreateSubTodoButtonClick(view: View)
    {
        serverListener?.onStarted()
        if (auth_token.isNullOrEmpty())
        {
            serverListener?.onFailure("Invalid Token")
            return
        }
        else if (subTodo?.name.isNullOrEmpty()) {
            serverListener?.onFailure("Invalid Title")
        }
        else
        {
            var todoResponse = TodoRepository().subTodoCreate(auth_token!!, mainTodo.id, subTodo.name, subTodo.done)
            serverListener?.onResponseSuccess(todoResponse)
        }

    }
    fun updateSubTodo()
    {
        serverListener?.onStarted()
        if (auth_token.isNullOrEmpty())
        {
            serverListener?.onFailure("Invalid Token")
            return
        }
        else if (subTodo?.name.isNullOrEmpty()) {
            serverListener?.onFailure("Invalid Title")
        }
        else
        {
            var todoResponse = TodoRepository().subTodoUpdate(auth_token!!, subTodo.todo_id, subTodo.id, subTodo.name, subTodo.done)
            serverListener?.onResponseSuccess(todoResponse)
        }

    }
    fun patchSubTodo()
    {
        serverListener?.onStarted()
        if (auth_token.isNullOrEmpty())
        {
            serverListener?.onFailure("Invalid Token")
            return
        }
        else
        {
            var todoResponse = TodoRepository().subTodoPatch(auth_token!!, subTodo.todo_id, subTodo.id, subTodo.done)
            serverListener?.onResponseSuccess(todoResponse)
        }

    }
}