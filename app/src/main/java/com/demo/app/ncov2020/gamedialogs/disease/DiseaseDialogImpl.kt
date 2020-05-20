package com.demo.app.ncov2020.gamedialogs.disease

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.viewpager.widget.ViewPager
import com.demo.app.ncov2020.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.disease_dialog.view.*

class DiseaseDialogImpl : DialogFragment(), DiseaseDialog {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.disease_dialog_2, container)
        val slidingTabs = rootView.findViewById<TabLayout>(R.id.sliding_tabs)
        val viewPager = rootView.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = DiseaseViewPagerAdapter(childFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        return rootView
    }

    override fun openDialog(fragmentManager: FragmentManager, tag: String) {
        show(fragmentManager, tag)
    }

}