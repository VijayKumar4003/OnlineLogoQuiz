package com.infowithvijay.onlinelogoquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.infowithvijay.onlinelogoquiz.LevelsActivity.category_id;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView qCount, timer;
    private Button option1, option2, option3, option4;

    private List<Question> questionList;

    private int quesNum;

    private CountDownTimer countDown;

    private int score;
    private int levelNo;

    FirebaseFirestore firestore;

    private Dialog dialog;

    private ImageView imgContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);


        qCount = findViewById(R.id.quest_num);
        timer = findViewById(R.id.timer);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        imgContent = findViewById(R.id.imgContent);


        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);


        firestore = FirebaseFirestore.getInstance();

        questionList = new ArrayList<>();


        levelNo = getIntent().getIntExtra("LEVELNO", 1);


        dialog = new Dialog(QuestionActivity.this);
        dialog.setContentView(R.layout.loding_progressbar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.progresss_background);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        getQuestionsList();

        score = 0;


    }

    private void getQuestionsList() {

        questionList = new ArrayList<>();


        firestore.collection("QUIZ").document("CAT" + String.valueOf(category_id))
                .collection("Level" + String.valueOf(levelNo))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {

                    QuerySnapshot questions = task.getResult();

                    for (QueryDocumentSnapshot doc : questions) {

                        questionList.add(new Question(
                                doc.getString("A"),
                                doc.getString("B"),
                                doc.getString("C"),
                                doc.getString("D"),
                                doc.getString("IMAGELOGO"),
                                Integer.valueOf(doc.getString("ANSWER"))
                        ));
                    }

                    setQuestion();


                } else {

                    Toast.makeText(QuestionActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }


                dialog.cancel();


            }
        });


    }

    private void setQuestion() {

        timer.setText(String.valueOf(10));

        option1.setText(questionList.get(0).getOptionA());
        option2.setText(questionList.get(0).getOptionB());
        option3.setText(questionList.get(0).getOptionC());
        option4.setText(questionList.get(0).getOptionD());
        qCount.setText(String.valueOf(1) + "/" + String.valueOf(questionList.size()));

        Picasso.get()
                .load(questionList.get(0).getImageURL())
                .into(imgContent);

        startTimer();
        quesNum = 0;

    }

    private void startTimer() {

        countDown = new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                if (millisUntilFinished < 100000) {
                    timer.setText(String.valueOf(millisUntilFinished / 1000));
                }

            }

            @Override
            public void onFinish() {

                changeQuestions();

            }
        };
        countDown.start();

    }

    private void changeQuestions() {

        enableButtons();

        if (quesNum < questionList.size() - 1) {

            quesNum++;

            playAnimation(option1, 0, 0);
            playAnimation(option2, 0, 1);
            playAnimation(option3, 0, 2);
            playAnimation(option4, 0, 3);
            playAnimation(imgContent, 0, 4);


            qCount.setText(String.valueOf(quesNum + 1) + "/" + String.valueOf(questionList.size()));


            timer.setText(String.valueOf(10));
            startTimer();

        } else {

            Intent intent = new Intent(QuestionActivity.this, Score.class);
            intent.putExtra("SCORE",String.valueOf(score));
            intent.putExtra("SIZEOFQUIZ",String.valueOf(questionList.size()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

    }


    private void playAnimation(final View view, final int value, final int viewNum) {

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(1000)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        if (value == 0) {

                            switch (viewNum) {

                                case 0:
                                    ((Button) view).setText(questionList.get(quesNum).getOptionA());
                                    break;
                                case 1:
                                    ((Button) view).setText(questionList.get(quesNum).getOptionB());
                                    break;
                                case 2:
                                    ((Button) view).setText(questionList.get(quesNum).getOptionC());
                                    break;
                                case 3:
                                    ((Button) view).setText(questionList.get(quesNum).getOptionD());
                                    break;

                                case 4:

                                    Picasso.get()
                                            .load(questionList.get(quesNum).getImageURL())
                                            .into(imgContent);
                                    break;
                            }

                            if (viewNum != 4) {
                                ((Button) view).setBackground(ContextCompat.getDrawable(getApplicationContext()
                                        , R.drawable.round_corner_for_quiz_option));

                            }

                            playAnimation(view, 1, viewNum);


                        }


                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        countDown.cancel();
    }

    private void enableButtons() {

        option1.setClickable(true);
        option2.setClickable(true);
        option3.setClickable(true);
        option4.setClickable(true);
    }

    private void disableButtons() {

        option1.setClickable(false);
        option2.setClickable(false);
        option3.setClickable(false);
        option4.setClickable(false);

    }


    @Override
    public void onClick(View v) {


        int selectedOption = 0;

        disableButtons();

        switch (v.getId()) {

            case R.id.option1:
                selectedOption = 1;
                break;
            case R.id.option2:
                selectedOption = 2;
                break;
            case R.id.option3:
                selectedOption = 3;
                break;
            case R.id.option4:
                selectedOption = 4;
                break;
        }

        countDown.cancel();

        checkAnswer(selectedOption, v);

    }

    private void checkAnswer(int selectedOption, View view) {

        if (selectedOption == questionList.get(quesNum).getCorrectAns()) {

            // Answer is right
            ((Button) view).setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_corner_for_correct_option));

            score++;


        } else {

            // Answer is wrong
            ((Button) view).setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_corner_for_wrong_option));


            switch (questionList.get(quesNum).getCorrectAns()) {

                case 1:
                    option1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_corner_for_correct_option));
                    break;
                case 2:
                    option1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_corner_for_correct_option));
                    break;
                case 3:
                    option1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_corner_for_correct_option));
                    break;
                case 4:
                    option1.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.round_corner_for_correct_option));
                    break;

            }
        }

        // Dealy

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

               changeQuestions();

            }
        },2000);


    }


}