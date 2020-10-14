package com.example.test.model

import androidx.databinding.BaseObservable

data class User (var name: String,
            var email: String,
            var password: String,
            var password_confirmation: String,
            var auth_token: String
): BaseObservable()