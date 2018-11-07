package com.test.quiz;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.test.quiz.Model.CheckedQuestion;
import com.test.quiz.Model.Exam;
import com.test.quiz.Model.OrderQuestion;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    private String Question_Table="Question";
    private String Help_table="Helps";
    private final String Result_Table="ExamResult";
    private final String Session_Table="Exam";
    //private final String Mabhas_Table="Exam";
    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DBHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getReadableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }
    /////////// Read Exams //////////
    public List<Exam> getExams(){
        List<Exam> exams=new ArrayList<>();
        Cursor cursor=database.rawQuery("select * from "+Session_Table,null);
        if(cursor.moveToFirst()){
            do{
                Exam exam=new Exam();
                exam.setExamNo(cursor.getString(cursor.getColumnIndex("ExamNo")));
                exam.setIsActive(cursor.getInt(cursor.getColumnIndex("isActive")));
                exams.add(exam);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return exams;
    }
    public void updateExam(String examNo){
        Cursor cursor=database.rawQuery("Select * from "+Session_Table + " where ExamNo="+examNo,null);
        ContentValues cv=new ContentValues();
        cv.put("isActive",1);
        if(cursor.moveToFirst()){
            database.update(Session_Table,cv,"ExamNo=?",
                    new String[]{examNo});
        }
    }
    /////////// CheckedQuestions ///////////////////
    public List<CheckedQuestion> getCheckedQuestions(String examNo) {
        List<CheckedQuestion> list;
        Cursor cursor = database.rawQuery("SELECT * From "+Question_Table+" where ExamId="+examNo,null);
        list=cursorToCheckedQuestion(cursor);
        cursor.close();
        return list;
    }
    public OrderQuestion getOrderQuestions(String examNo) {
        OrderQuestion order=new OrderQuestion();
        Cursor cursor = database.rawQuery("SELECT * From OrderQuestion where ExamId="+examNo,null);
        if(cursor.moveToFirst()){
                order.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                order.setExamId(cursor.getInt(cursor.getColumnIndex("ExamId")));
                order.setChoices(cursor.getString(cursor.getColumnIndex("listChoices")));
                order.setAnswers(cursor.getString(cursor.getColumnIndex("correctOrder")));
                order.setId(cursor.getInt(cursor.getColumnIndex("id")));
        }
        cursor.close();
        return order;
    }
    private List<CheckedQuestion> cursorToCheckedQuestion(Cursor cursor) {
        List<CheckedQuestion> list = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {CheckedQuestion temp=new CheckedQuestion();
                String[] ch=new String[4];
                ch[0]=cursor.getString(cursor.getColumnIndex("ch1"));
                temp.setCh(ch);
                temp.setTitle(cursor.getString(cursor.getColumnIndex("Title")));
                temp.setCorrectAnswers(cursor.getString(cursor.getColumnIndex("CorrectAns")));
                temp.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                temp.setImageId(cursor.getString(cursor.getColumnIndex("imageId")));
                temp.setUserAnswers(cursor.getString(cursor.getColumnIndex("userAns")));
                temp.setExamId(cursor.getInt(cursor.getColumnIndex("ExamId")));
                list.add(temp);
            }while (cursor.moveToNext());
        }
        return list;
    }
    /////// Helps ////////////////
    ///// Review Questions //////////
    public void insertResult(int ExamId,String wrngAns,String corrAns){
        Cursor cursor=database.rawQuery("Select * from "+Result_Table + " where ExamId="+ExamId,null);
        ContentValues cv=new ContentValues();
        cv.put("WrongAns",wrngAns);
        cv.put("CorrectAns",corrAns);
        if(cursor.moveToFirst()){
            cv.put("WrongAns",wrngAns);
            cv.put("CorrectAns",corrAns);
            database.update(Result_Table,cv,"Id=?",
                    new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex("Id")))});
        }
        else {
            cv.put("ExamId",ExamId);
            database.insert(Result_Table,null,cv);
        }

    }
    ///////// EndOfMabhas///////////////
}
