package com.test.quiz;

import android.content.Intent;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.test.quiz.Model.OrderQuestion;

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener {
    TextView Title;
    LinearLayout orderLayout;
    Button sendBtn;
    String sessionNo;
    OrderQuestion orderQuestion;
    String[] choices,answers,userAnswers;
    DatabaseAccess db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Title=findViewById(R.id.orderTitle);
        sendBtn=findViewById(R.id.submitOrder);
        orderLayout=findViewById(R.id.orderLayout);
        sendBtn.setOnClickListener(this);
        sessionNo=getIntent().getStringExtra("sessionNo" );
        db=DatabaseAccess.getInstance(this);
        db.open();
        orderQuestion=db.getOrderQuestions(sessionNo);
        Title.setText(orderQuestion.getTitle());
        choices=orderQuestion.getChoices().split(",,");
        answers=orderQuestion.getAnswers().split(",");
        prepareData();
    }

    private void prepareData() {
        userAnswers=new String[answers.length];
        for(int i=0;i<answers.length;i++){
            Spinner spinner=new Spinner(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 10, 10, 10);
            spinner.setLayoutParams(params);
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,choices);
            spinner.setAdapter(aa);
            final int finalI = i;
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    userAnswers[finalI]=String.valueOf(position+1);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            orderLayout.addView(spinner);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.submitOrder){
            int wrongCnt=0;
            for(int i=0;i<answers.length;i++){
                if(!answers[i].contains("or") && !answers[i].equals(userAnswers[i]))
                    wrongCnt=wrongCnt+1;
                else if(answers[i].contains("or")){
                    String[] ans=answers[i].split(" or ");
                    if(!ans[0].equals(userAnswers[i]) && !ans[1].equals(userAnswers[i]))
                        wrongCnt=wrongCnt+1;
                }
            }
            if(wrongCnt!=0)
                Toast.makeText(this,String.valueOf(wrongCnt)+" Wrong(s)",Toast.LENGTH_LONG).show();
            else {
                Toast.makeText(this,"Correct answers. New Session is Unlocked",Toast.LENGTH_LONG).show();
                db.updateExam(String.valueOf(Integer.valueOf(sessionNo)+1));
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
