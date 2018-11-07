package com.test.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.test.quiz.Adapters.CheckedQuestionAdapter;
import com.test.quiz.Model.CheckedQuestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SecondPageActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView questionListView;
    List<CheckedQuestion> questions=new ArrayList<>();
    String sessionNo;
    public static int onClickState=0;
    Button showAns,Continue;
    CheckedQuestionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        questionListView=findViewById(R.id.checkedQuestions);
        showAns=findViewById(R.id.showCorrectAnswers);
        Continue=findViewById(R.id.Continue);
        Continue.setOnClickListener(this);
        sessionNo=getIntent().getStringExtra("sessionNo" );
        showAns.setOnClickListener(this);
        onClickState=0;
        DatabaseAccess dbAccess=DatabaseAccess.getInstance(this);
        dbAccess.open();
        questions=dbAccess.getCheckedQuestions(sessionNo);
        for(int j=0;j<questions.size();j++){
            String[] ch=questions.get(j).getCh();
            String[] choices=ch[0].split(",,");
            String[][] helps=new String[choices.length][2];
            for(int i=0;i<choices.length;i++){
                int lastIndex=choices[i].length()-1;
                if(choices[i].contains("[")){
                    lastIndex=choices[i].indexOf("[");
                    helps[i][0]=choices[i].substring(0,lastIndex);
                    helps[i][1]=choices[i].substring(lastIndex+1,choices[i].length()-2);
                }
                else {
                    helps[i][0]=choices[i].substring(0,lastIndex);
                    helps[i][1]=null;
                }
            }
            questions.get(j).setTempHelp(helps);
        }
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        questionListView.setLayoutManager(manager);
        adapter=new CheckedQuestionAdapter(this,questions,0);
        questionListView.setAdapter(adapter);
        questionListView.setItemViewCacheSize(questions.size());
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.showCorrectAnswers){
            onClickState=1;
            showCorrectAns();
        }
        if(v.getId()==R.id.Continue){
            Intent intent=new Intent(this,ThirdActivity.class);
            intent.putExtra("sessionNo",sessionNo);
            startActivity(intent);
        }
    }

    private void showCorrectAns() {
        adapter.notifyDataSetChanged();
        String wrongQuestion="";
        String correctQ="";
        for(int i=0;i<questions.size();i++){
            String[] ans=questions.get(i).getCorrectAnswers().split(",");
            String userAns=questions.get(i).getUserAnswers();
            for(int j=0;j<ans.length;j++) {
                if (userAns == null || !userAns.contains(","+String.valueOf(ans[j])+","))
                    wrongQuestion=wrongQuestion+","+String.valueOf(i+1);
                else if(userAns.contains(","+String.valueOf(ans[j])+",")) {
                    correctQ = correctQ + "," + String.valueOf(i + 1);
                    userAns=userAns.replace(","+String.valueOf(ans[j])+",",",");
                }
                else
                    wrongQuestion=wrongQuestion+","+String.valueOf(i+1);
            }
            userAns=userAns.replaceAll(",","");
            if(userAns.length()>0)
                wrongQuestion=wrongQuestion+","+String.valueOf(i+1);
        }
        DatabaseAccess dbAccess=DatabaseAccess.getInstance(this);
        dbAccess.open();
        dbAccess.insertResult(questions.get(0).getExamId(),wrongQuestion,correctQ);
        if(wrongQuestion=="")
            Continue.setEnabled(true);
    }
}
