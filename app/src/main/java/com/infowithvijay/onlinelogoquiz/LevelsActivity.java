package com.infowithvijay.onlinelogoquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LevelsActivity extends AppCompatActivity {


    private GridView levels_grid;
    FirebaseFirestore firestore;

    private Dialog dialog;

    public static int category_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        Toolbar toolbar = findViewById(R.id.levels_toolbar);
        setSupportActionBar(toolbar);


        String title = getIntent().getStringExtra("CATEGORY");
        category_id = getIntent().getIntExtra("CATEGROY_ID",1);



        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        levels_grid = findViewById(R.id.levels_gridview);




        firestore = FirebaseFirestore.getInstance();


        dialog = new Dialog(LevelsActivity.this);
        dialog.setContentView(R.layout.loding_progressbar);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.progresss_background);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();


        loadLevels();

    }

    private void loadLevels() {

        firestore.collection("QUIZ").document("CAT" + String.valueOf(category_id))
                .get().addOnCompleteListener(task -> {

                if (task.isSuccessful()){


                    DocumentSnapshot doc = task.getResult();

                    if (doc.exists()){

                        long levels = (long) doc.get("Levels");
                        LevelsAdapter adapter = new LevelsAdapter((int) levels);
                        levels_grid.setAdapter(adapter);


                    }else {
                        Toast.makeText(this, "No Levels Document Exist", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }else {

                    Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }

            dialog.cancel();


        });


    }


}