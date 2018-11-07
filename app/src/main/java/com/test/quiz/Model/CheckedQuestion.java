package com.test.quiz.Model;


import android.widget.CheckBox;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;

public class CheckedQuestion {
    int id;
    int ExamId;
    String title;
    String correctAnswers;
    String userAnswers;
    String[] ch;
    String ImageId;
    String help;
    String[][] tempHelp;
    List<CheckBox> checkBoxes;
    String falseChain;

    public int getExamId() {
        return ExamId;
    }

    public void setExamId(int examId) {
        ExamId = examId;
    }

    public String getFalseChain() {
        return falseChain;
    }

    public void setFalseChain(String falseChain) {
        this.falseChain = falseChain;
    }

    public List<CheckBox> getCheckBoxes() {
        return checkBoxes;
    }

    public void setCheckBoxes(List<CheckBox> checkBoxes) {
        this.checkBoxes = checkBoxes;
    }

    public String[][] getTempHelp() {
        return tempHelp;
    }

    public void setTempHelp(String[][] tempHelp) {
        this.tempHelp = tempHelp;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getImageId() {
        return ImageId;
    }

    public void setImageId(String imageId) {
        ImageId = imageId;
    }

    public String getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(String userAnswers) {
        this.userAnswers = userAnswers;
    }

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getCh() {
        return ch;
    }

    public void setCh(String[] ch) {
        this.ch = ch;
    }
}
