package com.project.guessthatchord;

import android.support.v7.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ImageView audioButton;
    MediaPlayer mediaPlayer;
    Button optionA;
    Button optionB;
    Button optionC;
    Button optionD;
    Button submit;

    List<Question> questionList;
    private Question currentQuestion;
    private int count=0;
    private int totalQuestionCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper helper=new DbHelper(this);
        questionList=helper.getAllQuestions();
        totalQuestionCount=questionList.size();
        Collections.shuffle(questionList);

        audioButton=findViewById(R.id.playButton);
        optionA=findViewById(R.id.optionA);
        optionB=findViewById(R.id.optionB);
        optionC=findViewById(R.id.optionC);
        optionD=findViewById(R.id.optionD);
        submit=findViewById(R.id.submit);

        setQuestionOnScreen();

        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:to update db with new values, reinstall the app in the emulator
                try {
                    int resId=getResources().getIdentifier(currentQuestion.getAudioSource(),"raw",getPackageName());
                    mediaPlayer=MediaPlayer.create(MainActivity.this, resId);
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void setQuestionOnScreen(){

        currentQuestion=questionList.get(count);
        if(count<totalQuestionCount){
            optionA.setText(currentQuestion.getOptionA());
            optionB.setText(currentQuestion.getOptionB());
            optionC.setText(currentQuestion.getOptionC());
            optionD.setText(currentQuestion.getOptionD());
        }
        else{
            endQuiz();
        }
    }

    private void endQuiz(){
        finish();
    }

}
