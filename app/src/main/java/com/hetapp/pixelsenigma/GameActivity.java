package com.hetapp.pixelsenigma;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * Created by ruseck on 08.09.15.
 */
public class GameActivity extends MyActivity {

    private final int REQUEST_CODE_END_GAME = 2;
    private final int REQUEST_CODE_CHOOSE_COLOR = 1;
    private TextView tv_num_attempts;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private TextView button;
    public TextView tv_time;
    private TextView tv_score;
    private LayoutInflater inflater;
    private LinearLayout ll_history_attempts;
    private Game game;
    boolean isEnd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time.setTypeface(Typeface.createFromAsset(getAssets(), font));
        tv_num_attempts = (TextView) findViewById(R.id.tv_num_attempt);
        tv_num_attempts.setTypeface(Typeface.createFromAsset(getAssets(), font));
        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_score.setTypeface(Typeface.createFromAsset(getAssets(), font));
        tv_time.setText("Time: 80");
        tv_num_attempts.setText("8");
        tv_score.setText("Score: 0");
        ll_history_attempts = (LinearLayout) findViewById(R.id.ll_history_attempts);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView1.setTag(R.drawable.blue_pixel);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView2.setTag(R.drawable.blue_pixel);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView3.setTag(R.drawable.blue_pixel);
        imageView4 = (ImageView) findViewById(R.id.imageView4);
        imageView4.setTag(R.drawable.blue_pixel);
        button = (TextView) findViewById(R.id.tv_plus);
        inflater = getLayoutInflater();
        makeconvert();
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

    public void onPlus(View view) {
        if (isEnd) {
            Intent intent = new Intent(getApplicationContext(), GameResult.class);
            intent.putExtra("enigma", game.getEnigma());
            intent.putExtra("score", game.getScore());
            startActivityForResult(intent, REQUEST_CODE_END_GAME);
            return;
        }
        if (game == null) {
            game = new Game(4, 6, 8, 80, 40, 4, 2, 5, 40) {
                @Override
                void putAttemptGUI() {

                    View v = inflater.inflate(R.layout.item_lv, null);

                    ImageView imageView = (ImageView) v.findViewById(R.id.ib_r1);
                    imageView.setImageResource((int) imageView1.getTag());
                    imageView = (ImageView) v.findViewById(R.id.ib_r2);
                    imageView.setImageResource((int) imageView2.getTag());
                    imageView = (ImageView) v.findViewById(R.id.ib_r3);
                    imageView.setImageResource((int) imageView3.getTag());
                    imageView = (ImageView) v.findViewById(R.id.ib_r4);
                    imageView.setImageResource((int) imageView4.getTag());

                    ArrayList<Integer> attempt = new ArrayList<>();
                    attempt.add(convert.indexOf((Integer) imageView1.getTag()));
                    attempt.add(convert.indexOf((Integer) imageView2.getTag()));
                    attempt.add(convert.indexOf((Integer) imageView3.getTag()));
                    attempt.add(convert.indexOf((Integer) imageView4.getTag()));

                    int[] check = game.checkAttempt(attempt);

                    TextView textView = (TextView) v.findViewById(R.id.bb);
                    textView.setText("" + check[0]);
                    textView.setTypeface(Typeface.createFromAsset(getAssets(), font));
                    textView = (TextView) v.findViewById(R.id.bw);
                    textView.setText("" + check[1]);
                    textView.setTypeface(Typeface.createFromAsset(getAssets(), font));
                    ll_history_attempts.addView(v);

                    tv_num_attempts.setText("" + game.getNumAttempts());
                    tv_score.setText("Score: " + game.getScore());
                }

                @Override
                public void winActionGUI() {
                    ll_history_attempts.removeAllViews();
                    tv_num_attempts.setText("" + game.getNumAttempts());
                    tv_time.setText("Time: "+game.getTime());
                    tv_score.setText("Score: " + game.getScore());
                }

                @Override
                public void loseActionGUI() {
                    isEnd = true;
                    Intent intent = new Intent(getApplicationContext(), GameResult.class);
                    intent.putExtra("enigma", game.getEnigma());
                    intent.putExtra("score", game.getScore());
                    startActivityForResult(intent, REQUEST_CODE_END_GAME);
                }

                @Override
                public void timerGUI() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_time.setText("Time: " + game.getTime());
                        }
                    });
                }


            };
        }
        game.putAttempt(makeAttempt());
    }

    ArrayList<Integer> makeAttempt() {
        ArrayList<Integer> code = new ArrayList<>();
        code.add(convert.indexOf((int) imageView1.getTag()));
        code.add(convert.indexOf((int) imageView2.getTag()));
        code.add(convert.indexOf((int) imageView3.getTag()));
        code.add(convert.indexOf((int) imageView4.getTag()));
        return code;
    }

    private View view;

    public void onChooseColor(View view) {
        if (isEnd) return;
        startActivityForResult(new Intent(this, DialogChooseColor.class), REQUEST_CODE_CHOOSE_COLOR);
        this.view = view;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE_COLOR:
                    ((ImageView) view).setImageResource(convert.get(data.getIntExtra("color", 0)));
                    view.setTag(convert.get(data.getIntExtra("color", 0)));
                    break;
                case REQUEST_CODE_END_GAME:
                    startActivity(new Intent(this, GameActivity.class));
                    finish();
                    break;
            }
        }
    }
}
