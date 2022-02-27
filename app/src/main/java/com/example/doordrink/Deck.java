package com.example.doordrink;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

/**
 * Deck class,uses an arraylist of Questions. The deck contains the questions you've added.
 * Each question is suspended for MAX_SUSPENSION_ROUNDS after it's shown to avoid repetition.
 */
public class Deck  {
    private ArrayList<Question> sus;
    private Random rn;
    private int n;
    private MyDbHandler questionsDB;
    private ArrayList<Question> questions;


    public Deck(Context context){
        questionsDB=new MyDbHandler(context,"Questions.db",null,1);
        questions =new ArrayList<>();

        questions =questionsDB.getQuestions();

        sus=new ArrayList<>();
        rn=new Random();

    }

    public String pick(){
        n=rn.nextInt(questions.size());

        while(questions.get(n).isSuspended()){
            n=rn.nextInt(questions.size());
        }
        questions.get(n).suspend();

        sus.add(questions.get(n));
        inc();
        remove();
        if(sus.size()==questions.size()){
            forceRemove();
        }

        return questions.get(n).getQuestion();
    }

    private  void inc(){
        for (int i=0;i<sus.size();i++){
            sus.get(i).increase_rounds();
        }
    }

    /**
     * Removes questions from sus arraylist and they are playable again
     */
    private  void remove(){

        int i=0,size=sus.size();
        boolean no_question_removed=true;
        while (i<size && no_question_removed){
            if (sus.get(i).end_of_suspension()){
                sus.get(i).free();
                sus.remove(i);
                no_question_removed=false;
            }else{
                i++;
            }
        }
    }

    /**
     * If there are no more questions that are not suspended, the first 3 are removed.
     */
    private  void forceRemove(){

        for (int i=0;i<3;i++){
            sus.get(0).free();
            sus.remove(0);
        }
    }

    public void addToQuestions(String q){
        questionsDB.addQuestion(q);
        questions.add(new Question(q));
    }

}
