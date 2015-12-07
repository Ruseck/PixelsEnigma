package com.hetapp.pixelsenigma;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ruseck on 13.09.15.
 */
public class GameResult extends MyActivity {

    TextView tv_new_score;
    TextView tv_restart;
    int score;
    ArrayList<Integer>enigma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_game);

        enigma=(ArrayList<Integer>)getIntent().getSerializableExtra("enigma");
        score=getIntent().getIntExtra("score",0);

        makeconvert();

        tv_new_score = (TextView) findViewById(R.id.tv_new_score);
        tv_new_score.append(" " + score);
        tv_new_score.setTypeface(Typeface.createFromAsset(getAssets(), font));

        tv_restart = (TextView) findViewById(R.id.tv_restart);
        tv_restart.setTypeface(Typeface.createFromAsset(getAssets(), font));


        ImageView imageView = (ImageView) findViewById(R.id.ib_r1);
        imageView.setImageResource(convert.get(enigma.get(0)));
        imageView = (ImageView) findViewById(R.id.ib_r2);
        imageView.setImageResource(convert.get(enigma.get(1)));
        imageView = (ImageView) findViewById(R.id.ib_r3);
        imageView.setImageResource(convert.get(enigma.get(2)));
        imageView = (ImageView) findViewById(R.id.ib_r4);
        imageView.setImageResource(convert.get(enigma.get(3)));
    }

    ArrayList<Integer> convert;

    private void makeconvert() {
        convert = new ArrayList<>();
        convert.add(R.drawable.red_pixel);
        convert.add(R.drawable.orange_pixel);
        convert.add(R.drawable.yellow_pixel);
        convert.add(R.drawable.green_pixel);
        convert.add(R.drawable.blue_pixel);
        convert.add(R.drawable.purple_pixel);

    }

    public void onRestartGame(View view) {
        setResult(RESULT_OK);
        finish();
    }
}
