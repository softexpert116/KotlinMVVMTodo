package com.example.test.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.test.R
import com.example.test.model.TodoResponse
import com.example.test.ui.view.ServerListener
import com.example.test.util.hide
import com.example.test.util.myToast
import com.example.test.util.show
import com.example.test.view.MainActivity
import com.example.test.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_auth.*
import org.json.JSONObject

class AuthActivity : AppCompatActivity(), ServerListener {
    lateinit var viewModel:AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        viewModel.serverListener = this
    }

    override fun onStarted()
    {
        progressBar.show()
    }

    override fun onSuccess(response: LiveData<String>)
    {
        response.observe(this, Observer
        {
            progressBar.hide()
//            myToast(it)
            var msg:String
            val auth_token:String
            val jsonObject = JSONObject(it)
            if (jsonObject.has("message")) {
                msg = jsonObject.optString("message")
                myToast(msg)
            }
            if (jsonObject.has("auth_token")) {
                auth_token = jsonObject.optString("auth_token")
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("auth_token", auth_token)
                startActivity(intent)
                finish()
            }
        })
    }

    override fun onResponseSuccess(response: LiveData<TodoResponse>) {

    }

    override fun onFailure(message: String)
    {
        progressBar.hide()
        myToast(message)
    }



}
