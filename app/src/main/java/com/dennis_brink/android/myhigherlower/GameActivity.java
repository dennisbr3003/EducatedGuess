package com.dennis_brink.android.myhigherlower;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameActivity extends AppCompatActivity {

    private TextView textViewLast, textViewRight, textViewHint, textViewSummary, textViewFail;
    private Button buttonConfirm, buttonPlayAgain, buttonStop;
    private EditText editTextNumberGuess;
    private ImageView imageViewArrows, imageViewResult;
    private Animation animationBounce;
    private Animation animationBlink;
    private Animation animationFadeIn;
    private ArrayList<Integer> userInputList = new ArrayList<>();
    private Map<String, Integer> imageMap = new HashMap<>();

    int number=0;
    int attempts=10;
    int user_number=0;
    int index=0;

    String tag="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        fillImageMap();

        textViewHint = findViewById(R.id.textViewHint);
        textViewLast = findViewById(R.id.textViewLast);
        textViewRight = findViewById(R.id.textViewRight);
        textViewSummary = findViewById(R.id.textViewSummary);
        textViewFail = findViewById(R.id.textViewFail);

        textViewRight.setText(String.valueOf(attempts));
        editTextNumberGuess = findViewById(R.id.editTextNumberGuess);
        imageViewArrows = findViewById(R.id.imgArrowUpDown);
        imageViewResult = findViewById(R.id.imageViewResult);

        animationBounce = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        animationBlink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        animationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        animationBounce.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d("DENNIS_B", "animationBounce.onAnimationEnd");
                ImageView imageViewArrows = findViewById(R.id.imgArrowUpDown);
                Log.d("DENNIS_B", "animationBounce.onAnimationEnd imageViewArrows tag value " + imageViewArrows.getTag().toString());
                if(imageViewArrows.getTag().toString().equals("2")) {
                    imageViewArrows.startAnimation(animationBlink);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        buttonConfirm = findViewById(R.id.buttonConfirm);
        buttonPlayAgain = findViewById(R.id.buttonPlayAgain);
        buttonStop = findViewById(R.id.buttonStop);

        Intent i = getIntent();
        tag = i.getStringExtra("tag");  // possible values: two, three, four

        Log.d("DENNIS_B", "Radiobutton tag previous activity: " + tag);

        switch(tag){
            case "two":
                number = parseInt(RandomValue.getRandomNumericalString(2, false));
            break;
            case "three":
                number = parseInt(RandomValue.getRandomNumericalString(3, false));
            break;
            default: // would be the same as four, so why bother, if it's four goto default
                number = parseInt(RandomValue.getRandomNumericalString(4, false));
            break;
        }

        Log.d("DENNIS_B", "Generated number (no leading zero): " + number);

        editTextNumberGuess.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL || event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    Log.d("DENNIS_B", "GameActivity.class: (etxtNumberAnswer.onEditorAction) Answer submitted by keyboard <ok> ");
                    processUserInput();
                    return true;
                } else {
                    return false;
                }
            }
        });

        setFocusAndSHowKeyBoard();
        setupLogo();

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               processUserInput();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopApplication();
            }
        });

        buttonPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
            }
        });

    }

    private void playAgain() {
        backToMain();
    }

    private void processUserInput(){

        if(editTextNumberGuess.getText().toString().equals("")){
            Toast.makeText(GameActivity.this, "Please enter a value", Toast.LENGTH_LONG).show();
            return;
        }
        textViewHint.setVisibility(View.VISIBLE);
        textViewLast.setVisibility(View.VISIBLE);
        textViewRight.setVisibility(View.VISIBLE);
        imageViewArrows.setVisibility(View.VISIBLE);
        attempts--;
        index++;
        user_number=parseInt(editTextNumberGuess.getText().toString());
        userInputList.add(user_number);
        textViewLast.setText(String.valueOf(user_number));
        textViewRight.setText(String.valueOf(attempts));
        try {
            if (attempts == 0) {

                Log.d("DENNIS_B", "GameActivity.class: (processUserInput) number is not guessed by user");

                imageViewResult.setVisibility(View.INVISIBLE);
                editTextNumberGuess.setVisibility(View.INVISIBLE);
                buttonConfirm.setVisibility(View.INVISIBLE);
                textViewHint.setVisibility(View.INVISIBLE);
                imageViewArrows.setImageResource(android.R.color.transparent); // INVISIBLE or GONE did not work?
                hideKeyBoard();

                textViewFail.setText("OMG this is awful! I cannot believe you missed this one!");
                textViewFail.setVisibility(View.VISIBLE);

                //put some values below the entry textview
                buttonPlayAgain.setVisibility(View.VISIBLE);
                buttonStop.setVisibility(View.VISIBLE);
                textViewSummary.setText("You could not guess the generated number " + number + ".\n\n"
                                      + "Your guesses: \n" + userInputList
                                      + "\n\n Would you like to give it another shot?"
                );
                imageViewResult.setImageResource(imageMap.get("epic_fail"));
                imageViewResult.setVisibility(View.VISIBLE);
                imageViewResult.setAnimation(animationFadeIn); // not startAnimation
                return;
            }
            if (number == user_number) {

                Log.d("DENNIS_B", "GameActivity.class: (processUserInput) number is equal to user input");

                textViewHint.setText("Spot on! You must be some kind of psychic!");
                imageViewArrows.setImageResource(imageMap.get("success_2"));
                imageViewArrows.setTag("2");
                imageViewArrows.startAnimation(animationBounce);
                hideKeyBoard();
                editTextNumberGuess.setVisibility(View.INVISIBLE);
                buttonConfirm.setVisibility(View.INVISIBLE);
                buttonPlayAgain.setVisibility(View.VISIBLE);
                buttonStop.setVisibility(View.VISIBLE);
                textViewSummary.setText("Congratulations! \n\n You guessed the generated number " + number + ".\n"
                        + "It took you " + index + " attempts to guess it! \n\n"
                        + "Your guesses: \n" + userInputList
                        + "\n\n\n\n Would you like to play again?"
                );
                imageViewResult.setImageResource(imageMap.get("success_3"));
                imageViewResult.setVisibility(View.VISIBLE);
                return;
            }
            if (number > user_number) {
                textViewHint.setText("You may want to increase your guess...");
                imageViewArrows.setImageResource(imageMap.get("arrow_up"));
                imageViewArrows.setTag("1");
                imageViewArrows.startAnimation(animationBounce);
                return;
            }
            if (number < user_number) {
                textViewHint.setText("You may want to decrease your guess...");
                imageViewArrows.setImageResource(imageMap.get("arrow_down"));
                imageViewArrows.setTag("0");
                imageViewArrows.startAnimation(animationBounce);
                return;
            }
        }
        finally{
            editTextNumberGuess.setText("");
        }

    }
    private void setFocusAndSHowKeyBoard(){
        editTextNumberGuess.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void hideKeyBoard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editTextNumberGuess.getWindowToken(), 0);
    }

    private void stopApplication(){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    private void fillImageMap(){

        // a hashmap would probably be better than an arraylist (readability)
        imageMap.put("arrow_down", R.drawable.blue_arrow_down);
        imageMap.put("arrow_up", R.drawable.green_arrow_up);
        imageMap.put("success_2", R.drawable.success_2);
        imageMap.put("success_3", R.drawable.success_3);
        imageMap.put("epic_fail", R.drawable.epic_fail);
    }

    private void setupLogo(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.eg_logo_padding_game);
        getSupportActionBar().setTitle(getString(R.string._appname));
        getSupportActionBar().setSubtitle("Don't be a failure, get it right!");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }
    private void backToMain(){

        hideKeyBoard();

        // first parameter = from, second parameter what to start
        Intent i = new Intent(GameActivity.this, MainActivity.class);

        Log.d("DENNIS_B", "GameActivity.class: (backToMain) Restarting main intent");

        try {
            startActivity(i); // run it
            finish(); // close this one
        } catch (Exception e){
            Log.d("DENNIS_B", "GameActivity.class: (backToMain) --> " + e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        backToMain();
    }

}