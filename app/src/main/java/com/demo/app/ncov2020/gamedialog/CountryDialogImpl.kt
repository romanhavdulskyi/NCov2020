package com.demo.app.ncov2020.gamedialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class CountryDialogImpl : DialogFragment(), CountryDialog {
    private var countryName : String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun openDialog(countryName: String, fragmentManager: FragmentManager, tag: String) {
        this.countryName = countryName
        show(fragmentManager, tag)
    }

}