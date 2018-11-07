package com.test.quiz.Model;


public class Exam {
    int id;
    String ExamNo;
    int isActive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExamNo() {
        return ExamNo;
    }

    public void setExamNo(String examNo) {
        ExamNo = examNo;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
