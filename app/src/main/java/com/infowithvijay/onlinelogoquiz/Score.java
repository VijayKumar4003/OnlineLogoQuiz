package com.infowithvijay.onlinelogoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Score extends AppCompatActivity {


    private TextView score,txtCorrect,txtWrong;
    Button done;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        txtCorrect = findViewById(R.id.txtCorrect);
        txtWrong = findViewById(R.id.txtWrong);
        score = findViewById(R.id.sa_score);
        done = findViewById(R.id.btDone);

        String scoreData = getIntent().getStringExtra("SCORE");
        String sizeofquiz = getIntent().getStringExtra("SIZEOFQUIZ");

        int finalScore = Integer.parseInt(scoreData) * 10;

        score.setText(String.valueOf(finalScore));
        txtCorrect.setText(scoreData);

        int wrongQuestionsData = Integer.parseInt(sizeofquiz) - Integer.parseInt(scoreData);
        txtWrong.setText(String.valueOf(wrongQuestionsData));


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Score.this,Splash.class);
                startActivity(intent);
            }
        });


    }
}