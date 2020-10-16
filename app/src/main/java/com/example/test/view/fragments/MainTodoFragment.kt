package com.example.test.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.test.R
import com.example.test.model.MainTodo
import com.example.test.view.MainActivity
import com.example.test.view.adapter.MainTodoAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_main_todo.*

class MainTodoFragment : Fragment(R.layout.fragment_main_todo), MainTodoAdapter.MainTodoClickListener {
    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity.mainTodoAdapter = MainTodoAdapter(mainActivity, this, this, mainActivity.viewModel)
        mainActivity.getTodoListLiveData()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity.title = "Todo List"
        mainActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        mainActivity.fragment_index = 1
        recyclerView.apply {
            adapter = mainActivity.mainTodoAdapter
            hasFixedSize()
        }

        fab_create.setOnClickListener() {
            findNavController().navigate(R.id.createTodoFragment)
        }
        mainActivity.getTodoListLiveData()

    }

    override fun onMainTodoClicked(mainTodo: MainTodo) {
        val json_mainTodo = Gson().toJson(mainTodo)
        val bundle = bundleOf("json_mainTodo" to json_mainTodo)
        findNavController().navigate(R.id.subTodoFragment, bundle)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

}