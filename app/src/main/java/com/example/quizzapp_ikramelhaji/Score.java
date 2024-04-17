package com.example.quizzapp_ikramelhaji;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Score extends AppCompatActivity {


    Button bLogout, bTry;
    ProgressBar progressBar;
    TextView tvScore;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        tvScore =(TextView) findViewById(R.id.etscore);
        progressBar=(ProgressBar) findViewById(R.id.etprogress);
        bLogout=(Button) findViewById(R.id.bLogout);        bTry=(Button) findViewById(R.id.bTry);
        Intent intent=getIntent();
        score=intent.getIntExtra("score",0) ;
        progressBar.setProgress(100*score/5);
        tvScore.setText(100*score/5+" %");
        //Toast.makeText(getApplicationContext(),score+"",Toast.LENGTH_SHORT).show();
        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Merci de votre Participation !", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Score.this, Quiz.class));
            }
        });
        bTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Score.this, Quiz.class));
            }
        });

    }

}