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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        Start=findViewById(R.id.Start);
        Last_score=findViewById(R.id.last_score);
        Current_score=findViewById(R.id.current_score);

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_game();


            }
        });




    }

    public void open_game(){
        Intent game= new Intent(this, MainActivity.class);
        startActivity(game);


    }
}
