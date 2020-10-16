package com.example.test.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.test.App
import com.example.test.R
import com.example.test.model.MainTodo
import com.example.test.model.TodoResponse
import com.example.test.ui.view.ServerListener
import com.example.test.util.hide
import com.example.test.util.myToast
import com.example.test.util.show
import com.example.test.view.adapter.MainTodoAdapter
import com.example.test.view.adapter.SubTodoAdapter
import com.example.test.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), ServerListener {
    companion object {
    }

    lateinit var mainTodoAdapter: MainTodoAdapter
    lateinit var viewModel:TodoViewModel
    var auth_token:String? = null
    lateinit var selectedTodo : MainTodo
    var fragment_index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth_token = intent.getStringExtra("auth_token")
        viewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)
        viewModel.serverListener = this
        viewModel.auth_token = auth_token
    }
    fun getTodoListLiveData() {
        viewModel.todoListGet()?.observe(this, Observer {
            progressBar.hide()
            if (it != null) {
                if (mainTodoAdapter != null) {
                    mainTodoAdapter.updateData(it)
                }
            }
        })
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onFailure(message: String) {
        progressBar.hide()
        myToast(message)
    }

    override fun onStarted() {
        progressBar.show()
    }

    override fun onSuccess(response: LiveData<String>) {
        App.hideKeyboard(this)
        response.observe(this, Observer
        {
            if (it.equals("Successfully deleted") && fragment_index == 1) {
                getTodoListLiveData()
            }
            if (fragment_index == 3) {
                fragment_index = 1
                onBackPressed()
            }
        })
    }

    override fun onResponseSuccess(response: LiveData<TodoResponse>) {
        response.observe(this, Observer {
            myToast(it.message)
            progressBar.hide()
            if (fragment_index == 4) {
                fragment_index = 2
                onBackPressed()
            }
            if (it.mainTodo != null) {
                selectedTodo = it.mainTodo!!
                getTodoListLiveData()
            }
        })
    }

}