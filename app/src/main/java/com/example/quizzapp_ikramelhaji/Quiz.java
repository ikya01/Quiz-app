package com.example.quizzapp_ikramelhaji;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Quiz extends AppCompatActivity {
    private TextView questionTextView;
    private TextView questionNum;
    private ImageView questionImageView;
    private RadioButton rep1RadioButton;
    private RadioButton rep2RadioButton;
    private RadioGroup choicesRadioGroup;
    private Button nextButton;
    int cpt=1;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference questionsRef = db.collection("Question");
    private List<Question> questions = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionNum = findViewById(R.id.nquiz);
        questionTextView = findViewById(R.id.q);
        questionImageView = findViewById(R.id.image1);
        rep1RadioButton = findViewById(R.id.choice1);
        rep2RadioButton = findViewById(R.id.choice2);
        choicesRadioGroup = findViewById(R.id.choices);
        nextButton = findViewById(R.id.bnext);


        chargerQuestion();

        nextButton.setOnClickListener(v -> {
            int selectedId = choicesRadioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedAnswer = selectedRadioButton.getText().toString();
                String correctAnswer = questions.get(currentQuestionIndex).getRep();

                if (selectedAnswer.equals(correctAnswer)) {
                    score++;
                }
            } else {
                Toast.makeText(getApplicationContext(), "Choose an answer.", Toast.LENGTH_SHORT).show();
                return;
            }

            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                afficherQuestion();
            }
            else {
                Intent intent = new Intent(Quiz.this, Score.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }



        });



    }

    private void chargerQuestion() {
        questionsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Question question = document.toObject(Question.class);
                        questions.add(question);
                    }
                    afficherQuestion();
                } else {
                    Toast.makeText(getApplicationContext(), "Error !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void afficherQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionNum.setText("Question " +cpt);
            cpt++;
            questionTextView.setText(currentQuestion.getQuestion());
            Glide.with(this)
                    .load(currentQuestion.getImage())
                    .into(questionImageView);

            rep1RadioButton.setText(currentQuestion.getRep1());
            rep2RadioButton.setText(currentQuestion.getRep2());


            choicesRadioGroup.clearCheck();
        }
    }
}