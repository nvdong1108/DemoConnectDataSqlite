package com.vandong.democonnectdatasqlite;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase database = null;
    private static final String name = "data.sqlite";
    private static final String path = "/data/data/com.vandong.democonnectdatasqlite/databases/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doCreateDb();
        doDeleteDb();
        try {
            copydatabase();
            getListVocabulary("tb_congviec");
        } catch (IOException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void copydatabase() throws IOException {
        if (!checkDataBase()) {
            OutputStream myOutput = new FileOutputStream(path + name);

            byte[] buffer = new byte[1024];
            int length;
            InputStream myInput = this.getAssets().open("data.sqlite");
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myInput.close();
            myOutput.flush();
            myOutput.close();
        }
    }

    private ArrayList<String> getListVocabulary(String namtb) {
        ArrayList<String> list = new ArrayList<String>();
        Cursor c = null;
        c = database.query(namtb, null, null, null, null, null, null);
        String arr = "";
        while (c.moveToNext()) {
            arr += c.getString(2) + ", ";
        }
        Toast.makeText(this, arr, Toast.LENGTH_LONG).show();
        c.close();
        return list;
    }

    private boolean checkDataBase() {
        File dbFile = new File(path + name);
        Log.v("dbFile", dbFile + "   " + dbFile.exists());
        return dbFile.exists();
    }

    public void doCreateDb() {
        database = openOrCreateDatabase("data.sqlite", MODE_PRIVATE, null);
    }

    public void doDeleteDb() {
        deleteDatabase("data.sqlite");
    }
}
