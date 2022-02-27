package com.example.doordrink;

/**
 * Class that represents each question. Each question gets suspended
 * after it's first shown, for MAX_SUSPENSIO_ROUNDS to avoid repetition.
 */
public class Question {
    private boolean suspended_status;
    private String question;
    private int suspension_rounds;
    private final int MAX_SUSPENSION_ROUNDS=5;


    public Question(String q){
        question=q;
        suspended_status=false;
        suspension_rounds=0;
    }

    public boolean isSuspended(){
        return suspended_status;
    }

    public void suspend(){
        suspended_status=true;
        clear();
    }

    public void free(){
        suspended_status=false;
        clear();
    }

    public String getQuestion(){
        return question;
    }

    public void increase_rounds(){
        suspension_rounds++;
    }

    public boolean end_of_suspension(){
        return suspension_rounds>MAX_SUSPENSION_ROUNDS;

    }

    private void clear(){
        suspension_rounds=0;
    }
}
