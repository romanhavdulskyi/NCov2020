package com.demo.app.ncov2020.gamedialogs

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter

class ViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        return when(position)
        {
            0 -> DiseaseSymptomFragment()
            1 -> DiseaseAbilityFragment()
            2 -> DiseaseTransmissionFragment()
            3 -> DiseaseSymptomFragment()
            else
              ->  DiseaseSymptomFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }

}