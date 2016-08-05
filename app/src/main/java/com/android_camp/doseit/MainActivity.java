package com.android_camp.doseit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.client.Firebase;

public class MainActivity extends BaseActivity {
    static {
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }

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
        }
        startActivity(intent);
    }

}
