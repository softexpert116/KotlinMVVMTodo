package com.example.test.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.test.R
import com.example.test.databinding.ActivitySignupBinding
import com.example.test.model.TodoResponse
import com.example.test.util.hide
import com.example.test.util.myToast
import com.example.test.util.show
import com.example.test.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class SignupActivity : AppCompatActivity(), ServerListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySignupBinding = DataBindingUtil.setContentView(this,R.layout.activity_signup)
        var viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.serverListener = this

        btn_login.setOnClickListener() {
            finish()
        }
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
//                myToast(auth_token)
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
