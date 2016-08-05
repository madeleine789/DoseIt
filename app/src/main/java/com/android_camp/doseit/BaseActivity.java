package com.android_camp.doseit;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.content.DialogInterface;

/**
 * Created by demouser on 8/4/16.
 */
public class BaseActivity extends AppCompatActivity {

    public Toolbar toolbar;
    public boolean isMainActivity = true;

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorText));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_logo_small);
        toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BaseActivity.this, MainActivity.class);
                startActivity(i);
            }
        }
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public void helpAlert(){

        LayoutInflater inflater = LayoutInflater.from(this);
        View row;
        if (isMainActivity)
            row = inflater.inflate(R.layout.help,null);
        else
            row = inflater.inflate(R.layout.help_swipe,null);

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Help");
        alertDialog.setView(row);
        alertDialog.setIcon(R.drawable.ic_help_outline_black_48dp);

        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.ic_help:
                helpAlert();
                return true;
            case R.id.ic_report:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
