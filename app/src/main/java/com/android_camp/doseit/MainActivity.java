package com.android_camp.doseit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import com.android_camp.doseit.BaseActivity;

import com.firebase.client.Firebase;

import java.util.List;

public class MainActivity extends BaseActivity implements DataBaseHelper.Help {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        DataBaseHelper database = new DataBaseHelper();
        database.initDataBase(this);
    }

    public void onClickBtn(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.text_search_btn:
                intent = new Intent(this, SwipeActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Override
    public void dealWithData(List<Medicine> l)
    {
        for(Medicine m : l) {
            System.out.println("aici" + m.name + " " + m.kidDose);
        }
    }
}
