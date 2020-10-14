package com.example.test.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.test.R
import com.example.test.databinding.ActivityCreateTodoBinding
import com.example.test.model.TodoResponse
import com.example.test.ui.view.ServerListener
import com.example.test.util.hide
import com.example.test.util.myToast
import com.example.test.util.show
import com.example.test.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_login.*

class CreateTodoActivity : AppCompatActivity(), ServerListener {
    lateinit var viewModel:TodoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Create Todo"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val binding: ActivityCreateTodoBinding = DataBindingUtil.setContentView(this,R.layout.activity_create_todo)
        viewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.serverListener = this
        viewModel.auth_token = MainActivity.auth_token
    }

    override fun onStarted() {
        progressBar.show()
    }

    override fun onSuccess(response: LiveData<String>) {
        response.observe(this, Observer
        {
            progressBar.hide()
            myToast(it)
            finish()
        })

    }

    override fun onResponseSuccess(response: LiveData<TodoResponse>) {

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