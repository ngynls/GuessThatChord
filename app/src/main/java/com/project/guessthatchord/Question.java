package com.project.guessthatchord;

public class Question {

    private String audioSource;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;

    public Question(){
        audioSource="/audiofiles/test.wav";
        optionA="";
        optionB="";
        optionC="";
        optionD="";
        answer="";
    }

    public Question(String audioSource, String choiceA, String choiceB, String choiceC, String choiceD, String answer ){
        this.audioSource=audioSource;
        this.optionA=choiceA;
        this.optionB=choiceB;
        this.optionC=choiceC;
        this.optionD=choiceD;
        this.answer=answer;
    }

    public String getAudioSource(){
        return audioSource;
    }

    public String getOptionA(){
        return optionA;
    }

    public String getOptionB(){
        return optionB;
    }

    public String getOptionC(){
        return optionC;
    }

    public String getOptionD(){
        return optionD;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAudioSource(String audioSource) {
        this.audioSource = audioSource;
    }

    public void setChoiceA(String optionA){
        this.optionA=optionA;
    }

    public void setChoiceB(String optionB){
        this.optionB=optionB;
    }

    public void setChoiceC(String optionC){
        this.optionC=optionC;
    }

    public void setChoiceD(String optionD){
        this.optionD=optionD;
    }

    public void setAnswer(String answer){
        this.answer=answer;
    }
}
