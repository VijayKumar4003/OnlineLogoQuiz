package com.infowithvijay.onlinelogoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Splash extends AppCompatActivity {

    private ImageView appName;
    public static List<String> catList = new ArrayList<>();
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appName = findViewById(R.id.appName);

        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.myanimation);
        appName.setAnimation(anim);

        firestore = FirebaseFirestore.getInstance();


        new Thread(){

            public void run(){

               loadData();

            }

        }.start();

    }

    private void loadData() {

        catList.clear();

        firestore.collection("QUIZ").document("Categories")
                .get().addOnCompleteListener(task -> {

                    if (task.isSuccessful()){

                        DocumentSnapshot doc = task.getResult();

                        if (doc.exists()){

                            long count = (long) doc.get("COUNT");

                            for (int i = 1;i<=count;i++){
                                String catName = doc.getString("CAT" + String.valueOf(i));
                                catList.add(catName);
                            }

                            // Navigate to different Activity

                            Intent intent = new Intent(Splash.this,MainActivity.class);
                            startActivity(intent);
                            Splash.this.finish();


                        }else{

                            Toast.makeText(this, "No Category Document Exist", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }else {

                        Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }

        });

    }// loadData()

}