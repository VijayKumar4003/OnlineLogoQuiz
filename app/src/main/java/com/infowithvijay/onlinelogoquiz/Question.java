package com.infowithvijay.onlinelogoquiz;

class Question {

    String optionA;
    String optionB;
    String optionC;
    String optionD;
    int correctAns;
    String imageURL;


    public Question(String optionA, String optionB, String optionC, String optionD, String imageURL ,int correctAns) {
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.imageURL = imageURL;
        this.correctAns = correctAns;

    }


    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public int getCorrectAns() {
        return correctAns;
    }

    public String getImageURL() {
        return imageURL;
    }
}
