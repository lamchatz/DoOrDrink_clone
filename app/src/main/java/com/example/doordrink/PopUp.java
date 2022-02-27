package com.example.doordrink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Used to add you own questions
 * There is a TextView where you input your question and touch the done button.
 * When you are done, touch the close button and the questions are added in
 * the deck through a bundle and and intent
 */
public class PopUp extends AppCompatActivity {

    private EditText inputText;
    private ImageButton doneButton;
    private Button closeButton;
    private ArrayList<String> questions,sneaky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((width), (int) (height * .9));

        inputText = findViewById(R.id.inputEditText);
        doneButton = findViewById(R.id.doneButton);
        closeButton = findViewById(R.id.closeButton);

        questions=new ArrayList<>();
        sneaky=new ArrayList<>();

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String temp = inputText.getText().toString();
                inputText.setText("");
                questions.add(temp);
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("q",questions);
                intent.putExtra("b",bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);

            }
        });

        ConstraintLayout myLayout=findViewById(R.id.popUpLayout);
        myLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager)
                        v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                // Hide the soft keyboard
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });
    }


}