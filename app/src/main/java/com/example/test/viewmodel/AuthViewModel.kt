package com.example.test.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.test.data.repository.UserRepository
import com.example.test.model.User
import com.example.test.ui.view.ServerListener

class AuthViewModel : ViewModel(){
    var user = User("tester2", "tester2@email.com", "tester2", "tester2", "")
    var serverListener: ServerListener? = null
    fun onLoginButtonClick(view: View)
    {
        serverListener?.onStarted()
        if (user?.email.isNullOrEmpty() || user?.password.isNullOrEmpty())
        {
            serverListener?.onFailure("Invalid Email or Password")
            return
        }
        else
        {
            val response = UserRepository().userLogin(user?.email!!, user?.password!!)
            serverListener?.onSuccess(response)
        }
    }
    fun onSignupButtonClick(view: View)
    {
        serverListener?.onStarted()
        if (user?.name.isNullOrEmpty() || user?.email.isNullOrEmpty() || user?.password.isNullOrEmpty() || user?.password_confirmation.isNullOrEmpty())
        {
            serverListener?.onFailure("Invalid Name, Email or Password")
            return
        }
        else if (user?.password != user?.password_confirmation)
        {
            serverListener?.onFailure("Password does not match")
            return
        }
        else
        {
            val response = UserRepository().userSignup(user?.name!!, user?.email!!, user?.password!!, user?.password_confirmation!!)
            serverListener?.onSuccess(response)

        }

    }
}