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
import com.example.test.view.adapter.SubTodoAdapter
import com.example.test.viewmodel.TodoViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class ItemActivity : AppCompatActivity(), ServerListener, SubTodoAdapter.SubTodoClickListener {
    companion object {
        lateinit var mainTodo : MainTodo
    }

    lateinit var viewModel:TodoViewModel
    lateinit var subTodoAdapter: SubTodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        var json = intent.getStringExtra("todo_json")
        mainTodo = Gson().fromJson(json, MainTodo::class.java)
        title = "Items of " + mainTodo.title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)
        viewModel.serverListener = this
        viewModel.auth_token = MainActivity.auth_token
        subTodoAdapter = SubTodoAdapter(this, viewModel)
        recyclerView.apply {
            adapter = subTodoAdapter
            hasFixedSize()
        }

        fab_create.setOnClickListener() {
            val intent = Intent(this, CreateSubTodoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStarted() {
        progressBar1.show()
    }

    override fun onSuccess(response: LiveData<String>) {

    }

    override fun onResponseSuccess(response: LiveData<TodoResponse>) {
        response.observe(this, Observer
        {
            progressBar1.hide()
            myToast(it.message)
        })
    }

    override fun onFailure(message: String) {
        progressBar1.hide()
    }

    override fun onSubTodoClicked(mainTodo: MainTodo) {

    }

    override fun onResume() {
        super.onResume()
        subTodoAdapter.updateData(mainTodo.items)
    }
}