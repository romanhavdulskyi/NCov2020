package com.demo.app.ncov2020.gamedialogs.game_end;

import androidx.fragment.app.FragmentManager;

public interface GameEndDialogs {
    void showWinDialog(FragmentManager fragmentManager);
    void showLoseDialog(FragmentManager fragmentManager);
}
