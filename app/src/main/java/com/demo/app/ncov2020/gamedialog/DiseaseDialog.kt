package com.demo.app.ncov2020.gamedialog

import androidx.fragment.app.FragmentManager

interface DiseaseDialog {
    fun openDialog(fragmentManager: FragmentManager, tag : String)
}