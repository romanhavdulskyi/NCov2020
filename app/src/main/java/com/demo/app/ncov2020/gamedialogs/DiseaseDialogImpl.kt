package com.demo.app.ncov2020.gamedialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.demo.app.ncov2020.R

class DiseaseDialogImpl : DialogFragment(), DiseaseDialog {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.disease_dialog, container)
        val viewPager = rootView.findViewById<ViewPager>(R.id.viewPager)

        return rootView
    }

    override fun openDialog(fragmentManager: FragmentManager, tag: String) {
        show(fragmentManager, tag)
    }

}