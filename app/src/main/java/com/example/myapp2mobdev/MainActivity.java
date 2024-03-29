package com.example.myapp2mobdev;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private Button exitButton;
    private TextView textViewComment;
    private TextView textViewYourTime;
    private TextView textViewRequiredTime;
    private TextView textViewDifference;
    private TextView textViewScore;
    private TextView textViewStreak;
    private TextView textViewMultiplicator;
    private ImageView Heart5;
    private ImageView Heart4;
    private ImageView Heart3;
    private ImageView Heart2;
    private ImageView Heart1;
    private TextView textViewHighScore;
    private TextView textViewCurrentScore;
    private int gameState = 0;
    private long startTime;
    private int lifes = 5;
    private double yourTime;
    private int streak = 0;
    private int currentScore = 0;
    private int highScore = 0;
    private int score = 0;
    private double multiplicator = 1.0;
    private int roundCounter=0;
    private int randomGeneratedNumber = generateRandomNumber(10);
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            handler.postDelayed(this, 10); // Update timer every 10 milliseconds
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        highScore=getIntent().getIntExtra("highScore",0);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        textViewComment = findViewById(R.id.textViewComment);
        textViewYourTime = findViewById(R.id.textViewYourTime);
        textViewRequiredTime = findViewById(R.id.textViewRequiredTime);
        textViewDifference = findViewById(R.id.textViewDifference);
        textViewScore = findViewById(R.id.textViewScore);
        textViewStreak = findViewById(R.id.textViewStreak);
        textViewMultiplicator = findViewById(R.id.textViewMultiplicator);
        textViewCurrentScore=findViewById(R.id.textViewCurrentScore);
        textViewHighScore=findViewById(R.id.textViewHighScore);
        exitButton = findViewById(R.id.exitButton);

        String highScoreString ="Highscore : "+highScore;
        textViewHighScore.setText(highScoreString);

        String requiredTimeString;
        if(randomGeneratedNumber==1){
            requiredTimeString = "Try stopping the time at 1 second";
        }
        else{
            requiredTimeString = "Try stopping the time at " + randomGeneratedNumber + " seconds";
        }
        textViewRequiredTime.setText(requiredTimeString);
        
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_start();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                // gameState 0 Timer is being started
                if (gameState == 0) {
                    // Change button color to red and text to "Stop"
                    button.setBackgroundColor(getResources().getColor(R.color.red));
                    button.setText("Stop");
                    startTimer();
                    gameState++;
                    textViewComment.setText("Time is Running...");
                    textViewComment.setVisibility(View.VISIBLE);
                }
                // gameState 1 Timer is being stopped and Play again button and scores are visible
                else if (gameState == 1) {
                    stopTimer();
                    //show and initialize scoreboard
                    yourTime = (System.currentTimeMillis() - startTime) / 1000.0; // Set yourTime to elapsed time
                    String requiredTimeString;
                    if (randomGeneratedNumber == 1) {
                        requiredTimeString = "Goal were " + randomGeneratedNumber + " second";
                    } else {
                        requiredTimeString = "Goal were " + randomGeneratedNumber + " seconds";
                    }
                    textViewRequiredTime.setText(requiredTimeString);
                    textViewYourTime.setVisibility(View.VISIBLE);
                    String yourTimeString = "Your Time " + yourTime;
                    textViewYourTime.setText(yourTimeString);
                    textViewScore.setVisibility(View.VISIBLE);

                    //math of the difference
                    double difference = (yourTime * 1000 - randomGeneratedNumber * 1000) / 1000.0;
                    difference = Math.abs(difference); // Make sure the difference is positive
                    int differencePercent= (int) (difference/randomGeneratedNumber*100);
                    String differenceString = "Difference: " + difference + "/"+differencePercent+"%";

                    textViewDifference.setText(differenceString);
                    textViewDifference.setVisibility(View.VISIBLE);
                    button.setBackgroundColor(getResources().getColor(R.color.blue));
                    button.setText("Play Again");
                    gameState++;
                    //Perfect Score
                    if(differencePercent<5) {
                        //Setting the score
                        score=(int)(200*multiplicator);
                        String scoreString = "double points: "+score;
                        textViewScore.setText(scoreString);
                        //updating current score
                        currentScore=(int) (currentScore+200*multiplicator);
                        textViewCurrentScore.setText("Was ist denn hier los.");
                        String currentScoreString = "Current Score : " + currentScore;
                        //Change Comment
                        textViewComment.setText("Perfect!");
                        textViewComment.setTextColor(getResources().getColor(R.color.gold));
                        textViewCurrentScore.setText(currentScoreString);
                        //increasing Streak and multiplier
                        multiplicator=multiplicator+0.1;
                        streak++;
                    }

                    //Okay Score
                    else if (differencePercent<15) {
                        //Setting the score
                        score=(int)(100*multiplicator);
                        String scoreString = "Score: "+score;
                        textViewScore.setText(scoreString);
                        //updating current score
                        currentScore=(int) (currentScore+100*multiplicator);
                        textViewCurrentScore.setText("Was ist denn hier los.");
                        String currentScoreString = "Current Score : " + currentScore;
                        textViewCurrentScore.setText(currentScoreString);
                        //Change Comment
                        textViewComment.setTextColor(getResources().getColor(R.color.purple));
                        textViewComment.setHintTextColor(R.color.red);
                        textViewComment.setText("Decent");
                        //increasing Streak and multiplier
                        multiplicator=multiplicator+0.1;
                        streak++;
                    }

                    //Close Score
                    else if (differencePercent<=20) {
                        //Setting the score
                        score=(int)(50*multiplicator);
                        String scoreString = "Score: "+score;
                        textViewScore.setText(scoreString);
                        //updating current score
                        currentScore=(int) (currentScore+50*multiplicator);
                        textViewCurrentScore.setText("Was ist denn hier los.");
                        String currentScoreString = "Current Score : " + currentScore;
                        textViewCurrentScore.setText(currentScoreString);
                        //Change Comment
                        textViewComment.setTextColor(getResources().getColor(R.color.red));
                        textViewComment.setHintTextColor(R.color.red);
                        textViewComment.setText("Close One");
                        //increasing Streak and multiplier
                        multiplicator=multiplicator+0.1;
                        streak++;
                    }
                    //updating highscore if current score is higher
                    if (currentScore>highScore){
                        highScore=currentScore;
                        String highScoreString="Highscore : "+highScore;
                        textViewHighScore.setText(highScoreString);
                    }

                    //What happens if you lose
                    if (differencePercent > 20) {
                        lifes--;
                        DecreaseHearts(lifes);
                        textViewScore.setText("Score: 0");
                        textViewComment.setTextColor(getResources().getColor(R.color.red));
                        textViewComment.setHintTextColor(R.color.red);
                        if (lifes != 0) {
                            if (lifes > 1) {
                                textViewComment.setText("Ouch you Lost a Life");
                            } else if (lifes == 1) {
                                textViewComment.setText("This is your last life");
                            }
                            roundCounter--;
                            multiplicator=1.0;
                            streak=0;
                        }
                        //lost the game
                        else if (lifes == 0) {
                            textViewComment.setText("You lost!");
                            button.setBackgroundColor(getResources().getColor(R.color.dark_blue));
                            button.setText("Start over");
                            exitButton.setVisibility(View.VISIBLE);
                            gameState = 3;
                        }
                    }
                    String multiplicatorString="Multiplicator: "+ (1.0+(double) streak/10)+"x";
                    String streakString="Streak: "+streak;
                    textViewMultiplicator.setText(multiplicatorString);
                    textViewStreak.setText(streakString);
                    roundCounter++;
                }

                // random number gets created and required time gets shown start button is visible again.
                else if (gameState == 2) {
                    //make scoreboard invisible again
                    textViewYourTime.setVisibility(View.INVISIBLE);
                    textViewDifference.setVisibility(View.INVISIBLE);
                    textViewScore.setVisibility(View.INVISIBLE);
                    textViewComment.setVisibility(View.INVISIBLE);
                    textViewComment.setTextColor(getResources().getColor(R.color.black));
                    textViewComment.setText("Start the Timer");
                    textViewComment.setVisibility(View.VISIBLE);
                    // Change button color to green and text to "Start"
                    button.setBackgroundColor(getResources().getColor(R.color.green));
                    button.setText("Start");
                    //So the further you are with your streak the harder it is to score.
                    randomGeneratedNumber= generateRandomNumber(10+roundCounter*3);
                    String requiredTime;
                    if(randomGeneratedNumber==1){
                        requiredTime = "Try stopping the time at 1 second";
                    }
                    else{
                        requiredTime = "Try stopping the time at " + randomGeneratedNumber + " seconds";
                    }
                    textViewRequiredTime.setText(requiredTime);
                    gameState = 0;
                }
                //State where you have no lifes left and can either restart or go back to title.
                else if (gameState == 3) {
                    //if clicked exit button disappears.
                    exitButton.setVisibility(View.GONE);
                    streak=0;
                    score=0;
                    currentScore=0;
                    multiplicator=1.0;
                    lifes=5;
                    initializeStartValues();
                    gameState=0;
                    roundCounter=0;
                }
            }
        });
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        handler.postDelayed(runnable, 0);
    }

    private void stopTimer() {
        handler.removeCallbacks(runnable);
    }
    public static int generateRandomNumber(int interval) {
        Random random = new Random();
        return random.nextInt(interval-1) + 1;
    }
    public void DecreaseHearts(int activeLives){
        Heart5 = findViewById(R.id.imageView2);
        Heart4 = findViewById(R.id.imageView);
        Heart3 = findViewById(R.id.imageView5);
        Heart2 = findViewById(R.id.imageView3);
        Heart1 = findViewById(R.id.imageView4);
        if (activeLives<1){
            Heart1.setVisibility(View.INVISIBLE);
        }
        if (activeLives<2){
            Heart2.setVisibility(View.INVISIBLE);
        }
        if (activeLives<3){
            Heart3.setVisibility(View.INVISIBLE);
        }
        if (activeLives<4){
            Heart4.setVisibility(View.INVISIBLE);
        }
        if (activeLives<5){
            Heart5.setVisibility(View.INVISIBLE);
        }
    }
    public void resurrectHearts(){
        Heart5 = findViewById(R.id.imageView2);
        Heart4 = findViewById(R.id.imageView);
        Heart3 = findViewById(R.id.imageView5);
        Heart2 = findViewById(R.id.imageView3);
        Heart1 = findViewById(R.id.imageView4);
        Heart1.setVisibility(View.VISIBLE);
        Heart2.setVisibility(View.VISIBLE);
        Heart3.setVisibility(View.VISIBLE);
        Heart4.setVisibility(View.VISIBLE);
        Heart5.setVisibility(View.VISIBLE);
    }
    public void go_start(){
        Intent intent= new Intent(this, Start.class);
        String highScoreString= "High Score\n"+highScore;
        String lastScoreString="Last Score\n"+currentScore;
        intent.putExtra("HighScore",highScoreString);
        intent.putExtra("CurrentScore",lastScoreString);
        intent.putExtra("highScoreInt",highScore);
        startActivity(intent);
    }

    public void initializeStartValues(){
        //
        textViewComment = findViewById(R.id.textViewComment);
        textViewYourTime = findViewById(R.id.textViewYourTime);
        textViewRequiredTime = findViewById(R.id.textViewRequiredTime);
        textViewDifference = findViewById(R.id.textViewDifference);
        textViewScore = findViewById(R.id.textViewScore);
        textViewStreak = findViewById(R.id.textViewStreak);
        textViewMultiplicator = findViewById(R.id.textViewMultiplicator);
        textViewCurrentScore=findViewById(R.id.textViewCurrentScore);
        textViewHighScore=findViewById(R.id.textViewHighScore);
        exitButton = findViewById(R.id.exitButton);
        textViewYourTime.setVisibility(View.INVISIBLE);
        textViewDifference.setVisibility(View.INVISIBLE);
        textViewStreak.setText("Streak: 0");
        textViewMultiplicator.setText("Multiplicator: 1.0x");
        textViewCurrentScore.setText("Current Score : 0");
        textViewScore.setVisibility(View.INVISIBLE);
        textViewComment.setTextColor(getResources().getColor(R.color.black));
        textViewComment.setText("Are you a perfect stopwatch?");
        button.setBackgroundColor(getResources().getColor(R.color.green));
        button.setText("Start");
        String requiredTimeString;
        randomGeneratedNumber= generateRandomNumber(10);
        resurrectHearts();
        if(randomGeneratedNumber==1){
            requiredTimeString = "Try stopping the time at 1 second";
        }
        else{
            requiredTimeString = "Try stopping the time at " + randomGeneratedNumber + " seconds";
        }
        textViewRequiredTime.setText(requiredTimeString);

    }

}
