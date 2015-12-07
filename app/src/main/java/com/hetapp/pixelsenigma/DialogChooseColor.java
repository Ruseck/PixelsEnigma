package com.hetapp.pixelsenigma;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by ruseck on 11.09.15.
 */
public class DialogChooseColor extends Activity {

    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_color);
    }

    public void onMakeChoise(View view) {
        switch(view.getId()){
            case R.id.iv1:
                color=0;
                break;
            case R.id.iv2:
                color=1;
                break;
            case R.id.iv3:
                color=2;
                break;
            case R.id.iv4:
                color=3;
                break;
            case R.id.iv5:
                color=4;
                break;
            case R.id.iv6:
                color=5;
                break;
        }
        setResult(RESULT_OK,new Intent().putExtra("color",color));
        finish();
    }

}
