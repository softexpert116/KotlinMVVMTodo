package com.example.test.model

import androidx.databinding.BaseObservable

data class SubTodo (var id: Int,
                    var name: String,
                    var done: Boolean,
                    var todo_id: Int,
                    var created_at: String,
                    var updated_at: String
): BaseObservable()
