package com.project.guessthatchord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TrainingModeActivity extends AppCompatActivity {

    //Declare button elements as member variables
    Button mcqButton;
    Button sttButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_mode_screen);

        mcqButton=(Button) findViewById(R.id.mcqButton);
        sttButton=(Button) findViewById(R.id.sttButton);

        mcqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        sttButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),SttActivity.class);
                startActivity(intent);
            }
        });
    }
}