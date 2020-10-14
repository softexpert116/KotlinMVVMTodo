package com.example.test.model

import androidx.databinding.BaseObservable

data class MainTodo (var id: Int,
                     var title: String,
                     var created_by: String,
                     var created_at: String,
                     var updated_at: String,
                     var items:MutableList<SubTodo>
): BaseObservable()