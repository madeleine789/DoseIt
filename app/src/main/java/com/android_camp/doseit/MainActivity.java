package com.android_camp.doseit;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.firebase.client.Firebase;

public class MainActivity extends BaseActivity {
    static {
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }

    ImageButton mic, camera, search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isMainActivity = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolBar();
        mic = (ImageButton) findViewById(R.id.voice_search_btn);
        mic.setOnTouchListener(new ButtonAnimationListener(mic));
        camera = (ImageButton) findViewById(R.id.photo_search_btn);
        camera.setOnTouchListener(new ButtonAnimationListener(camera));
        search = (ImageButton) findViewById(R.id.text_search_btn);
        search.setOnTouchListener(new ButtonAnimationListener(search));
        Animation a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin);
        mic.startAnimation(a);
        camera.startAnimation(a);
        search.startAnimation(a);
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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

    private class ButtonAnimationListener implements View.OnTouchListener {

        ImageButton button;
        ButtonAnimationListener(ImageButton button) {
            this.button = button;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    button.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomout));
                    break;

                case MotionEvent.ACTION_UP:
                    button.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoomin));
                    break;
            }
            return false;
        }
    }

}
