package com.project.guessthatchord;

public class Question {

    private String audioSource;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String hint;
    private String answer;
    private String answer_verbose;

    public Question(){
        audioSource="/audiofiles/test.wav";
        optionA="";
        optionB="";
        optionC="";
        optionD="";
        hint="";
        answer="";
        answer_verbose="";
    }

    public Question(String audioSource, String choiceA, String choiceB, String choiceC, String choiceD, String hint, String answer, String answer_verbose ){
        this.audioSource=audioSource;
        this.optionA=choiceA;
        this.optionB=choiceB;
        this.optionC=choiceC;
        this.optionD=choiceD;
        this.hint=hint;
        this.answer=answer;
        this.answer_verbose=answer_verbose;
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

    public String getHint(){
        return hint;
    }

    public String getAnswer(){
        return answer;
    }

    public String getAnswer_verbose() {
        return answer_verbose;
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

    public void setHint(String hint){
        this.hint=hint;
    }

    public void setAnswer(String answer){
        this.answer=answer;
    }

    public void setAnswer_verbose(String answer_verbose){
        this.answer_verbose=answer_verbose;
    }
}
