package com.test.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.test.quiz.Adapters.MainListAdapter;
import com.test.quiz.Model.Exam;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView sessionList;
    List<Exam> sessionTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionList=findViewById(R.id.sessionList);
        DatabaseAccess db=DatabaseAccess.getInstance(this);
        db.open();
        sessionTitle=db.getExams();
        MainListAdapter adapter=new MainListAdapter(sessionTitle,this);
        //ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,sessionTitle);
        sessionList.setDrawingCacheEnabled(true);
        sessionList.setAdapter(adapter);
        //sessionList.setOnItemClickListener(this);
    }

}
