package com.example.test.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.test.R
import com.example.test.model.MainTodo
import com.example.test.model.TodoResponse
import com.example.test.ui.view.ServerListener
import com.example.test.util.hide
import com.example.test.util.myToast
import com.example.test.util.show
import com.example.test.view.adapter.MainTodoAdapter
import com.example.test.viewmodel.TodoViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ServerListener, MainTodoAdapter.MainTodoClickListener {
    companion object {
        var auth_token:String? = null
    }
    lateinit var viewModel:TodoViewModel

    lateinit var mainTodoAdapter: MainTodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Todo List"
        auth_token = intent.getStringExtra("auth_token")
        viewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)
        viewModel.serverListener = this
        viewModel.auth_token = auth_token
        mainTodoAdapter = MainTodoAdapter(this, this, viewModel)
        recyclerView.apply {
            adapter = mainTodoAdapter
            hasFixedSize()
        }

        fab_create.setOnClickListener() {
            val intent = Intent(this, CreateTodoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onFailure(message: String) {
        progressBar1.hide()
        myToast(message)
    }

    override fun onStarted() {
        progressBar1.show()
    }

    override fun onSuccess(response: LiveData<String>) {
        response.observe(this, Observer
        {
            myToast(it)
            if (it.equals("Successfully deleted")) {
                observeTodoList()
            }
        })
    }

    override fun onResponseSuccess(response: LiveData<TodoResponse>) {

    }

    override fun onMainTodoClicked(mainTodo: MainTodo) {
        val json = Gson().toJson(mainTodo)
        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra("todo_json", json)
        startActivity(intent)
    }

    fun observeTodoList() {
        viewModel.todoListGet()?.observe(this, Observer {
            progressBar1.hide()
            if (it != null) {
                mainTodoAdapter.updateData(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        observeTodoList()
    }
}