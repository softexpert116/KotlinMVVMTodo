package com.example.test.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.test.R
import com.example.test.databinding.ActivityCreateSubtodoBinding
import com.example.test.model.TodoResponse
import com.example.test.ui.view.ServerListener
import com.example.test.util.hide
import com.example.test.util.myToast
import com.example.test.util.show
import com.example.test.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_login.*

class CreateSubTodoActivity : AppCompatActivity(), ServerListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Add Item in " + ItemActivity.mainTodo.title

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val binding: ActivityCreateSubtodoBinding = DataBindingUtil.setContentView(this,R.layout.activity_create_subtodo)
        var viewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.serverListener = this
        viewModel.mainTodo = ItemActivity.mainTodo
        viewModel.auth_token = MainActivity.auth_token
    }

    override fun onStarted() {
        progressBar.show()
    }

    override fun onSuccess(response: LiveData<String>) {


    }

    override fun onResponseSuccess(response: LiveData<TodoResponse>) {
        response.observe(this, Observer
        {
            progressBar.hide()
            myToast(it.message)
            ItemActivity.mainTodo = it.mainTodo!!
            finish()
        })
    }

    override fun onFailure(message: String) {
        progressBar.hide()
        myToast(message)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}