package com.example.test.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.test.R
import com.example.test.databinding.FragmentSignupBinding
import com.example.test.view.AuthActivity
import kotlinx.android.synthetic.main.fragment_login.*

class SignupFragment : Fragment() {

    lateinit var authActivity: AuthActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentSignupBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_signup, container, false)
        binding.viewmodel = authActivity.viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_login.setOnClickListener() {
            authActivity.onBackPressed()
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        authActivity = context as AuthActivity
    }

}