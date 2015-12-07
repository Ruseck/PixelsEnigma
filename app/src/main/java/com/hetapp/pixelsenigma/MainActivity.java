package com.hetapp.pixelsenigma;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends MyActivity {

    private TextView tv_start;
    private TextView tv_app_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView(tv_app_name, R.id.tv_app_name,font);
        textView(tv_start,R.id.tv_start,font);
    }

    private void textView(TextView tv, int res, String font) {
        tv = (TextView) findViewById(res);
        tv.setTypeface(Typeface.createFromAsset(getAssets(), font));
    }

    public void onStartGame(View v) {
        startActivity(new Intent(this, GameActivity.class));
    }

}
