package com.demo.app.ncov2020.gamedialogs.game_end

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.demo.app.ncov2020.R
import com.demo.app.ncov2020.common.GameNavigatorImpl
import com.demo.app.ncov2020.userprofile.login.UserProfileAuthenticatorImpl

class GameEndDialogsImpl : DialogFragment(), GameEndDialogs {

    private var isWin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme)
    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.end_game_dialog_layout, container, false)
        val messageView = rootView.findViewById<TextView>(R.id.messageView)
        if(isWin)
            messageView.text = "You won!"
        else
            messageView.text = "You lose!"
        val closeBtn = rootView.findViewById<Button>(R.id.closeBtn)
        closeBtn.setOnClickListener {
            UserProfileAuthenticatorImpl.instance.logout()
            GameNavigatorImpl.instance.navigateToLogin()
            dismiss() }
        return rootView
    }

    override fun showWinDialog(fragmentManager: FragmentManager) {
        isWin = true
        show(fragmentManager, "END_GAME")
    }
    override fun showLoseDialog(fragmentManager: FragmentManager) {
        isWin = false
        show(fragmentManager, "END_GAME")
    }
}