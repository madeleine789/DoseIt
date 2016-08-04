package com.android_camp.doseit;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;

import com.android_camp.doseit.BaseActivity;

public class MainActivity extends BaseActivity {

    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
    }

    public void onClickBtn(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.text_search_btn:
                intent = new Intent(this, SwipeActivity.class);
                break;
            case R.id.voice_search_btn:
                intent = new Intent(getApplicationContext(), VoiceActivity.class);
                break;

        }
        startActivity(intent);
    }

}
