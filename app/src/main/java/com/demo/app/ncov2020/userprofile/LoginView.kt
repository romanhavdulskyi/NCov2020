package com.demo.app.ncov2020.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.demo.app.basics.mvvm.BaseView
import com.demo.app.ncov2020.R
import com.demo.app.ncov2020.databinding.LoginFragmentBinding

class LoginView(lifecycleOwner: LifecycleOwner,
                inflater: LayoutInflater,
                container: ViewGroup?,
                model: LoginViewModel) : BaseView() {

    init {
        val mViewBinding: LoginFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)
        mViewBinding.lifecycleOwner = lifecycleOwner
        mViewBinding.viewModel = model
        mViewBinding.executePendingBindings()
        viewLayout = mViewBinding.root
    }


}