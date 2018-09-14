package com.project.guessthatchord;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView audioButton;
    MediaPlayer mediaPlayer;
    Dialog dialogBox;
    Button [] btn=new Button[4];
    int[] btn_id={R.id.optionA,R.id.optionB,R.id.optionC,R.id.optionD};
    ImageView correctAnswerImg, incorrectAnswerImg, starImg;
    Button nextQuestion, tryAgain, returnToMenu;

    List<Question> questionList;
    private Question currentQuestion;
    private int count=0;
    private int totalQuestionCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialogBox=new Dialog(this);

        DbHelper helper=new DbHelper(this);
        questionList=helper.getAllQuestions();
        totalQuestionCount=questionList.size();
        Collections.shuffle(questionList);

        audioButton=findViewById(R.id.playButton);
        for(int i=0; i<btn.length;i++){
            btn[i]=findViewById(btn_id[i]);
            btn[i].setOnClickListener(this);
        }

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
        if(count<totalQuestionCount){
            currentQuestion=questionList.get(count);
            btn[0].setText(currentQuestion.getOptionA());
            btn[1].setText(currentQuestion.getOptionB());
            btn[2].setText(currentQuestion.getOptionC());
            btn[3].setText(currentQuestion.getOptionD());
            count++;
        }
        else{
            endQuiz();
        }
    }

    private void endQuiz(){
        displayEndDialog();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.optionA:
                checkAnswer(0);
                break;
            case R.id.optionB:
                checkAnswer(1);
                break;
            case R.id.optionC:
                checkAnswer(2);
                break;
            case R.id.optionD:
                checkAnswer(3);
                break;
        }
    }

    private void checkAnswer(int i) {
        String yourAnswer=btn[i].getText().toString();
        if(yourAnswer.equals(currentQuestion.getAnswer())){
            Log.i("log","Correct answer!");
            displayPositiveDialog();
            Log.i("log","Count is now " + count + " out of " + totalQuestionCount);
            if(count==totalQuestionCount)
                endQuiz();
        }
        else{
            Log.i("log","Wrong answer!"+" The correct answer is " + currentQuestion.getAnswer());
            displayNegativeDialog();
        }

    }


    private void displayPositiveDialog() {
        dialogBox.setContentView(R.layout.popup_correct_answer);
        correctAnswerImg=(ImageView)dialogBox.findViewById(R.id.correctAnswerImg);
        nextQuestion=(Button)dialogBox.findViewById(R.id.nextQuestion);

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setQuestionOnScreen();
                dialogBox.dismiss();
            }
        });

        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBox.show();

    }

    private void displayNegativeDialog() {
        dialogBox.setContentView(R.layout.popup_incorrect_answer);
        incorrectAnswerImg=(ImageView)dialogBox.findViewById(R.id.wrongAnswerImg);
        tryAgain=(Button)dialogBox.findViewById(R.id.tryAgain);

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBox.dismiss();
            }
        });

        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBox.show();
    }

    private void displayEndDialog(){
        dialogBox.setContentView(R.layout.popup_end);
        starImg=(ImageView)dialogBox.findViewById(R.id.starImg);
        returnToMenu=(Button)dialogBox.findViewById(R.id.returnToHome);

        returnToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:go back to the main menu not the training mode
                dialogBox.dismiss();
                finish();
            }
        });
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBox.show();
    }
}
