package com.demo.app.ncov2020.gamedialogs.disease

import androidx.fragment.app.FragmentManager

interface DiseaseDialog {
    fun openDialog(fragmentManager: FragmentManager, tag : String)
}