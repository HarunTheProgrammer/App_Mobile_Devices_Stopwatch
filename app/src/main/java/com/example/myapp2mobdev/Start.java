package com.example.myapp2mobdev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Start extends AppCompatActivity {


    private Button Start;
    private TextView Last_score;
    private TextView Current_score;
    private String CurrentScore="Last Score\n0000";
    private String HighScore= "High Score\n0000";
    private int highScoreInt=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        highScoreInt=getIntent().getIntExtra("highScoreInt",0);
        if(highScoreInt!=0) {
            CurrentScore = getIntent().getStringExtra("CurrentScore");
            HighScore = getIntent().getStringExtra("HighScore");
        }
        setContentView(R.layout.start);
        Start=findViewById(R.id.Start);
        Last_score=findViewById(R.id.last_score);
        Current_score=findViewById(R.id.high_score);
        Current_score.setText(HighScore);
        Last_score.setText(CurrentScore);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_game();
            }
        });
    }

    public void open_game(){
        Intent game= new Intent(this, MainActivity.class);
        game.putExtra("highScore",highScoreInt);
        startActivity(game);
    }
}
