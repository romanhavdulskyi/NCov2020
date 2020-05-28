package com.demo.app.ncov2020.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.demo.app.basics.mvvm.BaseFragment

import com.demo.app.ncov2020.common.ViewModelProvider
import com.demo.app.ncov2020.userprofile.login.LoginStates

class LoginFragment : BaseFragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider.getInstance()?.getViewModel(LoginViewModel::class.java) as LoginViewModel
        viewModel.loginLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { login ->
                if (login.state == LoginStates.SUCCESS) {
                    viewModel.loginIsSucceeded()
                }
            }
        })
        val loginView = LoginView(this, inflater, container, viewModel)
        return loginView.viewLayout
    }

}
