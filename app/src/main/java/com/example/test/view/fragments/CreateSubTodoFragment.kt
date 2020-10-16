package com.example.test.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.test.R
import com.example.test.databinding.FragmentCreateSubTodoBinding
import com.example.test.model.SubTodo
import com.example.test.view.MainActivity

class CreateSubTodoFragment : Fragment() {

    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity.title = "Create SubTodo"
        mainActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mainActivity.viewModel.mainTodo = mainActivity.selectedTodo
        mainActivity.fragment_index = 4
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentCreateSubTodoBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_create_sub_todo, container, false)
        mainActivity.viewModel.subTodo = SubTodo(0, "", false, 0, "", "")
        binding.viewmodel = mainActivity.viewModel
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
}