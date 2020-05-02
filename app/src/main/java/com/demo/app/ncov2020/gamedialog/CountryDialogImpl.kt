package com.demo.app.ncov2020.gamedialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.demo.app.ncov2020.R
import com.demo.app.ncov2020.common.ColorUtils
import com.demo.app.ncov2020.game.Game
import com.demo.app.ncov2020.game.GameProvider


class CountryDialogImpl(private val gameProvider: GameProvider) : DialogFragment(), CountryDialog, GameProvider.Client {
    private var countryName: String? = null
    private lateinit var healthyProgressBar : ProgressBar
    private lateinit var infectedProgressBar : ProgressBar
    private lateinit var deadProgressBar : ProgressBar
    private lateinit var countryTitle : TextView
    private lateinit var infectedPeopleTitle : TextView
    private lateinit var deadPeopleTitle : TextView
    private lateinit var closeBtn : AppCompatImageButton
    private lateinit var infectBtn : AppCompatButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.country_dialog, container)
        healthyProgressBar = rootView.findViewById(R.id.healthyProgressBar)
        infectedProgressBar = rootView.findViewById(R.id.infectedProgressBar)
        deadProgressBar = rootView.findViewById(R.id.deadProgressBar)
        countryTitle = rootView.findViewById(R.id.countryTitle)
        closeBtn = rootView.findViewById(R.id.closeBtn)
        closeBtn.setOnClickListener{ dismiss() }
        infectBtn = rootView.findViewById(R.id.infect_btn)
        infectBtn.setOnClickListener{
            countryName?.let { it1 -> gameProvider.infectCountry(it1) }
        }

        infectedPeopleTitle = rootView.findViewById(R.id.infectedPeopleTitle)
        deadPeopleTitle = rootView.findViewById(R.id.deadPeopleTitle)
        countryTitle.text = countryName
        val progressDrawable: Drawable = healthyProgressBar.progressDrawable.mutate()
        progressDrawable.setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN)
        healthyProgressBar.progressDrawable = progressDrawable
        val progressDrawable1: Drawable = infectedProgressBar.progressDrawable.mutate()
        progressDrawable1.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
        infectedProgressBar.progressDrawable = progressDrawable1
        val progressDrawable2: Drawable = deadProgressBar.progressDrawable.mutate()
        progressDrawable2.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
        deadProgressBar.progressDrawable = progressDrawable2
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
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onChanged(state: Game) {
        val country = state.infectedCountries[countryName]
        handler.post {
            if (country != null && country.isInfected) {
                countryTitle.setTextColor(ColorUtils.genColor(country.percentOfInfectedPeople))
                infectedProgressBar.visibility = View.VISIBLE
                deadProgressBar.visibility = View.VISIBLE
                infectedPeopleTitle.visibility = View.VISIBLE
                deadPeopleTitle.visibility = View.VISIBLE
                infectBtn.visibility = View.GONE
                healthyProgressBar.max = (country.amountOfPeople / 1000).toInt()
                healthyProgressBar.setProgress((country.healthyPeople / 1000).toInt(), true)
                infectedProgressBar.max = (country.amountOfPeople / 1000).toInt()
                infectedProgressBar.setProgress((country.infectedPeople / 1000).toInt(), true)
                deadProgressBar.max = (country.amountOfPeople / 1000).toInt()
                deadProgressBar.setProgress((country.deadPeople / 1000).toInt(), true)
            } else {
                countryTitle.setTextColor(Color.WHITE)
                infectedProgressBar.visibility = View.GONE
                deadProgressBar.visibility = View.GONE
                infectedPeopleTitle.visibility = View.GONE
                deadPeopleTitle.visibility = View.GONE
                infectBtn.visibility = View.VISIBLE
            }
        }
    }

}