package com.demo.app.ncov2020.gamedialogs.disease

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.demo.app.ncov2020.gamedialogs.disease.DiseaseAbilityFragment
import com.demo.app.ncov2020.gamedialogs.disease.DiseaseSymptomFragment
import com.demo.app.ncov2020.gamedialogs.disease.DiseaseTransmissionFragment

class DiseaseViewPagerAdapter(fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        return when(position)
        {
            0 -> DiseaseAbilityFragment()
            1 -> DiseaseTransmissionFragment()
            2 -> DiseaseSymptomFragment()
            else
              -> DiseaseSymptomFragment()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position)
        {
            0 -> "Abilities"
            1 -> "Transmissions"
            2 -> "Symptoms"
            else
            ->    super.getPageTitle(position)
        }
    }

}