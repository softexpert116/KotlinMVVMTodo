package com.example.test.view.fragments

import android.R.attr.data
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.test.R
import com.example.test.databinding.FragmentCreateTodoBinding
import com.example.test.view.MainActivity

class CreateTodoFragment : Fragment() {
    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity.title = "Create Todo"
        mainActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mainActivity.fragment_index = 3
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentCreateTodoBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_create_todo, container, false)
        mainActivity.viewModel.mainTodo.title = ""
        binding.viewmodel = mainActivity.viewModel
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

}