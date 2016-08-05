package com.android_camp.doseit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.firebase.client.Firebase;

public class MainActivity extends BaseActivity {
    static {
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isMainActivity = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
    }

    public void cameraAlert(){

        Log.d("MSG", "clicked");
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Sorry");
        alertDialog.setMessage("This function has not been implemented yet, comming soon.");
        alertDialog.setIcon(R.drawable.ic_camera_alt_black_24dp);

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Can't wait to see it.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "It dosen't matter.",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void onClickBtn(View view) {
        Log.d("MSG", "clicked");

        Intent intent = null;
        switch (view.getId()) {
            case R.id.text_search_btn:
                intent = new Intent(this, SwipeActivity.class);
                startActivity(intent);
                break;
            case R.id.photo_search_btn:
                cameraAlert();
                break;
        }
    }

}
