package com.demo.app.ncov2020.data;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.demo.app.NConApp;
import com.demo.app.basics.concurrency.Fault;
import com.demo.app.basics.concurrency.TaskCallback;
import com.demo.app.ncov2020.common.CSVConverter;
import com.demo.app.ncov2020.data.dao.CommonCountryDao;
import com.demo.app.ncov2020.data.room_data.CommonCountry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import timber.log.Timber;

public class CommonCountryRepo {
    private Executor executor = Executors.newSingleThreadExecutor();
    private CSVConverter listConverter = new CSVConverter();

    public void fetchAll(TaskCallback<List<CommonCountry>> taskCallback) {
        executor.execute(() -> {
            List<CommonCountry> commonCountries = new ArrayList<>();
            DataBaseHelper dataBaseHelper = new DataBaseHelper(NConApp.Companion.getContext());
            try {
                dataBaseHelper.createDatabase();
            } catch (IOException e) {
                e.printStackTrace();
            }
            SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();

            @SuppressLint("Recycle") Cursor cur = sqLiteDatabase.rawQuery("select * from CommonCountry", null);

            if (cur.moveToFirst()) {
                do {
                    CommonCountry commonCountry = new CommonCountry(cur.getInt(0),
                            cur.getString(1),
                            cur.getString(2),
                            cur.getLong(3),
                            cur.getInt(4) > 0,
                            listConverter.toStringList(cur.getString(5)),
                            listConverter.toStringList(cur.getString(6)),
                            listConverter.toStringList(cur.getString(7)),
                            listConverter.toStringList(cur.getString(8)),
                            cur.getInt(9),
                            cur.getInt(10));
                    commonCountries.add(commonCountry);
                } while (cur.moveToNext());

            }

            taskCallback.onCompleted(commonCountries, null);
        });

    }
}
