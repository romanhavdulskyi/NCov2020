package com.demo.app.ncov2020.gamedialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.demo.app.ncov2020.R
import com.demo.app.ncov2020.common.ColorUtil
import com.demo.app.ncov2020.common.TextUtils
import com.demo.app.ncov2020.game.Game
import com.demo.app.ncov2020.game.GameProvider
import com.hookedonplay.decoviewlib.DecoView
import com.hookedonplay.decoviewlib.charts.DecoDrawEffect
import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent
import com.hookedonplay.decoviewlib.events.DecoEvent.ExecuteEventListener
import timber.log.Timber


class CountryDialogImpl(private val gameProvider: GameProvider) : DialogFragment(), CountryDialog, GameProvider.Client {
    private var countryName: String? = null
    private lateinit var mDecoView : DecoView
    private lateinit var countryTitle : TextView
    private lateinit var infoView : TextView
    private lateinit var closeBtn : AppCompatImageButton
    private lateinit var infectBtn : AppCompatButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.country_dialog, container)
        mDecoView = rootView.findViewById(R.id.dynamicArcView)
        countryTitle = rootView.findViewById(R.id.countryTitle)
        infoView = rootView.findViewById(R.id.infoView)
        closeBtn = rootView.findViewById(R.id.closeBtn)
        closeBtn.setOnClickListener{ dismiss() }
        infectBtn = rootView.findViewById(R.id.infect_btn)
        infectBtn.setOnClickListener{
            countryName?.let { it1 -> gameProvider.infectCountry(it1) }
        }

        countryTitle.text = countryName

        createBackSeries()
        createDataSeries1()
        createDataSeries2()
        createDataSeries3()

        gameProvider.addClient(this)
        return rootView
    }

    override fun onDestroyView() {
        gameProvider.removeClient(this)
        super.onDestroyView()
    }

    override fun openDialog(countryName: String, fragmentManager: FragmentManager, tag: String) {
        this.countryName = countryName
        show(fragmentManager, tag)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private val handler = Handler()
    private var mBackIndex = 0
    private var mSeries1Index = 0
    private var mSeries2Index = 0
    private var mSeries3Index = 0
    private val mSeriesMax = 50f
    private var wasFullyInfected = false

    @SuppressLint("SetTextI18n")
    override fun onChanged(state: Game) {
        val country = state.infectedCountries[countryName]
        handler.post {
            if (country != null && country.isInfected) {
                countryTitle.setTextColor(ColorUtil.genColor(country.percentOfInfAndDeadPeople))
                infectBtn.visibility = View.GONE
                infoView.visibility = View.VISIBLE
                infoView.text = TextUtils.floatToText((country.percentOfInfAndDeadPeople * 100).toFloat())   + " %"

                Timber.e("Values %f, %f, %f", country.percentOfHealthyPeople * 50, country.percentOfInfectedPeople * 50, country.percentOfDeadPeople * 50)
                mDecoView.addEvent(DecoEvent.Builder((country.percentOfHealthyPeople * 50).toFloat())
                        .setIndex(mSeries1Index)
                        .setDelay(0)
                        .setDuration(0)
                        .build())

                if(!wasFullyInfected) {
                    mDecoView.addEvent(DecoEvent.Builder((country.percentOfInfectedPeople * 50).toFloat())
                            .setIndex(mSeries2Index)
                            .build())
                }

                mDecoView.addEvent(DecoEvent.Builder((country.percentOfDeadPeople * 50).toFloat())
                        .setIndex(mSeries3Index)
                        .build())
                wasFullyInfected = country.percentOfHealthyPeople.compareTo(0.0f) == 0

            } else {
                infoView.visibility = View.GONE
                countryTitle.setTextColor(Color.WHITE)
                infectBtn.visibility = View.VISIBLE
            }
        }
    }

    private fun createBackSeries() {
        val seriesItem = SeriesItem.Builder(Color.parseColor("#FFE2E2E2"))
                .setRange(0f, mSeriesMax, 50f)
                .setInitialVisibility(true)
                .build()
        mBackIndex = mDecoView.addSeries(seriesItem)
    }

    private fun createDataSeries1() {
        val seriesItem = SeriesItem.Builder(Color.parseColor("#FF6699FF"))
                .setRange(0f, mSeriesMax, 0f)
                .setInitialVisibility(false)
                .build()
        mSeries1Index = mDecoView.addSeries(seriesItem)
    }

    private fun createDataSeries2() {
        val seriesItem = SeriesItem.Builder(Color.parseColor("#FFFF4444"))
                .setRange(0f, mSeriesMax, 0f)
                .setInitialVisibility(false)
                .build()

        mSeries2Index = mDecoView.addSeries(seriesItem)
    }

    private fun createDataSeries3() {
        val seriesItem = SeriesItem.Builder(Color.parseColor("#000000"))
                .setRange(0f, mSeriesMax, 0f)
                .setInitialVisibility(false)
                .build()
        mSeries3Index = mDecoView.addSeries(seriesItem)
    }

}