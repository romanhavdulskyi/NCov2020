package com.demo.app.ncov2020.gamedialogs.disease

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.viewpager.widget.ViewPager
import com.demo.app.ncov2020.R
import com.demo.app.ncov2020.game.Game
import com.demo.app.ncov2020.game.GameProvider
import com.demo.app.ncov2020.game.GameProviderImpl
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.disease_dialog.view.*

class DiseaseDialogImpl : DialogFragment(), DiseaseDialog, GameProvider.Client {

    private lateinit var infoView : TextView
    private lateinit var closeBtn : AppCompatImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.disease_dialog_2, container)
        val slidingTabs = rootView.findViewById<TabLayout>(R.id.sliding_tabs)
        infoView = rootView.findViewById(R.id.infoView)
        closeBtn = rootView.findViewById(R.id.closeBtn)
        closeBtn.setOnClickListener { dismiss() }
        val viewPager = rootView.findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = DiseaseViewPagerAdapter(childFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        GameProviderImpl.INSTANCE.addClient(this)
        return rootView
    }

    override fun onDestroyView() {
        GameProviderImpl.INSTANCE.removeClient(this)
        super.onDestroyView()
    }

    override fun openDialog(fragmentManager: FragmentManager, tag: String) {
        show(fragmentManager, tag)
    }

    val handler = Handler()
    override fun onChanged(state: Game) {
        handler.post {
            infoView.text = "Points: " + state.upgradePoints
        }
    }

}