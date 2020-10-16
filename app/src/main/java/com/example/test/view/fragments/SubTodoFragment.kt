package com.example.test.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.test.R
import com.example.test.model.MainTodo
import com.example.test.view.MainActivity
import com.example.test.view.adapter.SubTodoAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_sub_todo.*

class SubTodoFragment : Fragment(R.layout.fragment_sub_todo) {
    lateinit var mainActivity:MainActivity
    lateinit var subTodoAdapter: SubTodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        var json = bundle!!.getString("json_mainTodo")
        mainActivity.selectedTodo = Gson().fromJson(json, MainTodo::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mainActivity.title = mainActivity.selectedTodo.title
        mainActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mainActivity.fragment_index = 2

        subTodoAdapter = SubTodoAdapter(mainActivity, mainActivity.viewModel)
        subTodoAdapter.updateData(mainActivity.selectedTodo.items)
        recyclerView.apply {
            adapter = subTodoAdapter
            hasFixedSize()
        }

        fab_create.setOnClickListener() {
            findNavController().navigate(R.id.createSubTodoFragment)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
}