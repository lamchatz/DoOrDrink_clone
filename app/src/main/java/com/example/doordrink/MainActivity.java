package com.example.doordrink;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Stack;

/**
 * The main game activity. If the user long clicks the welcome text a popup
 * appears where he can add questions.
 */
public class MainActivity extends AppCompatActivity {

    private boolean played_once;
    private Deck deck;
    private Stack<String> next;
    private Stack<String> back;
    private int counter=0;
    private TextView questionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        questionView=findViewById(R.id.questionView);

        questionView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (counter==0){
                    Intent intent=new Intent(MainActivity.this,PopUp.class);

                    startActivity(intent);
                    return true;
                }else{
                    return false;
                }
            }
        });

        played_once=false;
        deck=new Deck(getApplicationContext());
        back=new Stack<>();
        next=new Stack<>();


        final ImageView nextArrow=findViewById(R.id.right);
        nextArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pick();
            }
        });


        final ImageView backArrow=findViewById(R.id.left);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               back();
            }
        });
    }

    private void pick(){
        String q="";

        if (counter<=2){
            counter++;
            if (counter==2){
                played_once=true;
            }
        }

        if (!next.empty()){
            q=pop(next);
            push(back,questionView.getText().toString());
        }else {
            q=deck.pick();
            if(played_once){
                push(back,questionView.getText().toString());
            }
        }

        questionView.setText(q);
    }

    private void back(){
        if (!back.empty() && played_once){
            push(next,questionView.getText().toString());
            questionView.setText(back.pop());
        }
    }

    private String pop(Stack<String> s){
        return s.pop();
    }

    private void push(Stack<String> s,String i){
        s.push(i);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle=intent.getBundleExtra("b");
        ArrayList<String> q=bundle.getStringArrayList("q");
        if (q.size()>0){
            for (int i=0;i<q.size();i++){
                deck.addToQuestions(q.get(i));
            }
        }

    }
}