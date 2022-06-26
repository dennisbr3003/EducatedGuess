package com.dennis_brink.android.myhigherlower;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;
    private RadioButton rbTwo, rbThree, rbFour;
    private RadioGroup rgrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupLogo();

        btnStart = findViewById(R.id.btnStart);
        rbTwo = findViewById(R.id.rbTwo);
        rbThree = findViewById(R.id.rbThree);
        rbFour = findViewById(R.id.rbFour);
        rgrp = findViewById(R.id.radioGroup);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, GameActivity.class);

                int count = rgrp.getChildCount();
                int p=0;
                for(int t=0;t<count;t++){
                    View v = rgrp.getChildAt(t);
                    if(v instanceof RadioButton){
                        Log.d("DENNIS_B", "Radiobutton tag: " + v.getTag() + " is checked: " + ((RadioButton) v).isChecked());
                        if(((RadioButton) v).isChecked()){
                            i.putExtra("tag", String.valueOf(v.getTag()));
                            p++;
                        }
                    }
                }
                if(p==0){
                    Snackbar snackbar = Snackbar.make(view, "You need to make a selection", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    return;
                } else {
                    startActivity(i);
                    finish();
                }

            }
        });

    }

    private void setupLogo(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.eg_main_logo);
        getSupportActionBar().setTitle(getString(R.string._appname));
        getSupportActionBar().setSubtitle(getString(R.string._mainsub));
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

}