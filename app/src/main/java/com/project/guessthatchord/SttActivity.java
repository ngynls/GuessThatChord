package com.project.guessthatchord;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SttActivity extends AppCompatActivity {

    ImageView audioButton, micButton;
    MediaPlayer mediaPlayer;
    Dialog dialogBox;
    ImageView correctAnswerImg, incorrectAnswerImg, starImg;
    TextView hint;
    Button nextQuestion, tryAgain, returnToMenu, hintButton;

    List<Question> questionList;
    private Question currentQuestion;
    private int count=0;
    private int totalQuestionCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stt);

        dialogBox=new Dialog(this);

        DbHelper helper=DbHelper.getInstance(this);
        questionList=helper.getAllQuestions();
        totalQuestionCount=questionList.size();
        Collections.shuffle(questionList);

        audioButton=findViewById(R.id.playButton);
        micButton=findViewById(R.id.micIcon);
        hintButton=findViewById(R.id.hintButton);

        hint=findViewById(R.id.hint);

        setQuestionOnScreen();
        setButtonsOnClickListener();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        outState.putInt("savedCount", count);
        outState.putString("savedAudio",currentQuestion.getAudioSource());
        outState.putString("savedHint",currentQuestion.getHint());
        outState.putString("savedAnswer",currentQuestion.getAnswer_verbose());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState){
        super.onRestoreInstanceState(outState);
        count=outState.getInt("savedCount");
        currentQuestion.setHint(outState.getString("savedHint"));
        currentQuestion.setAudioSource(outState.getString("savedAudio"));
        currentQuestion.setAnswer_verbose(outState.getString("savedAnswer"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200){
            if(resultCode== RESULT_OK && data!=null){
                ArrayList<String> result=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                Log.i("log","You said " + result.get(0));
                String formattedAnswer=result.get(0).substring(0,1).toUpperCase()+result.get(0).substring(1);
                Log.i("log","Formatted answer is " + formattedAnswer);
                checkAnswer(formattedAnswer);
            }
        }
    }

    protected void setButtonsOnClickListener(){
        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int resId=getResources().getIdentifier(currentQuestion.getAudioSource(),"raw",getPackageName());
                    mediaPlayer=MediaPlayer.create(SttActivity.this, resId);
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,R.string.prompt);
                try{
                    startActivityForResult(intent,200);
                }
                catch(ActivityNotFoundException a){
                    Toast.makeText(getApplicationContext(),"No intent",Toast.LENGTH_SHORT).show();
                }
            }
        });

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hint.setText(currentQuestion.getHint());
            }
        });
    }

    protected void setQuestionOnScreen() {
        if(count<totalQuestionCount){
            currentQuestion=questionList.get(count);
            count++;
        }
        else
            endQuiz();
    }

    private void checkAnswer(String yourAnswer){
        if(yourAnswer.equals(currentQuestion.getAnswer_verbose())){
            displayPositiveDialog();
            if(count==totalQuestionCount)
                endQuiz();
        }
        else
            displayNegativeDialog();
    }

    private void endQuiz() {
        displayEndDialog();
    }

    private void displayPositiveDialog() {
        dialogBox.setContentView(R.layout.popup_correct_answer);
        correctAnswerImg=(ImageView)dialogBox.findViewById(R.id.correctAnswerImg);
        nextQuestion=(Button)dialogBox.findViewById(R.id.nextQuestion);

        nextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setQuestionOnScreen();
                hint.setText(R.string.emptyStr);
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
