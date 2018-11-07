package com.test.quiz.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.quiz.CustomViews.CustomLinearLayoutManager;
import com.test.quiz.Model.CheckedQuestion;
import com.test.quiz.R;
import com.test.quiz.SecondPageActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CheckedQuestionAdapter extends RecyclerView.Adapter<CheckedQuestionAdapter.CustomViewHolder> {
    List<CheckedQuestion> questions;
    Context context;
    int state;
    public CheckedQuestionAdapter(@NonNull Context context, List<CheckedQuestion> questions,int state) {
        this.questions=questions;
        this.context=context;
        this.state=state;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.checked_question_item,parent,false);
        CustomViewHolder viewHolder=new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        holder.onBind(position);
        List<CheckBox> checkBoxes=questions.get(position).getCheckBoxes();
        for(int i=0;i<checkBoxes.size();i++){
            final int finalI = i;
            checkBoxes.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userAnswers=questions.get(position).getUserAnswers();
                    if(userAnswers==null) userAnswers=",";
                    if(((CheckBox)v).isChecked()){
                        String hint=(questions.get(position).getTempHelp())[finalI][1];
                        if(hint!=null){
                            holder.help.setText(hint);
                            holder.help.setVisibility(View.VISIBLE);
                            if(questions.get(position).getFalseChain()==null)
                                questions.get(position).setFalseChain((questions.get(position).getTempHelp())[finalI][0]);
                            else{
                                String ss=questions.get(position).getFalseChain()+","+(questions.get(position).getTempHelp())[finalI][0];
                                questions.get(position).setHelp(ss);
                            }
                        }
                        else {
                            holder.help.setVisibility(View.INVISIBLE);
                        }
                        userAnswers=userAnswers+String.valueOf(finalI+1)+",";
                    }
                    else
                        userAnswers=userAnswers.replace(String.valueOf(finalI+1),"");
                    questions.get(position).setUserAnswers(userAnswers);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return questions.size();}
    public  class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView questionTitle,help;
        ImageView imageView;
        LinearLayout dynamicLayout;
        public CustomViewHolder(View view) {
            super(view);
            questionTitle=view.findViewById(R.id.questionTitle);
            help=view.findViewById(R.id.help);
            imageView=view.findViewById(R.id.qImage);
            dynamicLayout=view.findViewById(R.id.dynamicLayout);
        }
        public void onBind(int position){
            if(questions.get(position).getImageId()==null)
                imageView.setVisibility(View.GONE);
            else
                imageView.setImageResource(R.drawable.im1);
            questionTitle.setText(position+1+":"+ questions.get(position).getTitle());
            String[][] choices=questions.get(position).getTempHelp();
            if(questions.get(position).getCheckBoxes()==null)
                for(int i=0;i<choices.length;i++){
                    CheckBox checkBox=new CheckBox(context);
                    checkBox.setText(choices[i][0]);
                    if(questions.get(position).getCheckBoxes()==null)
                        questions.get(position).setCheckBoxes(new ArrayList<CheckBox>());
                    questions.get(position).getCheckBoxes().add(checkBox);
                    dynamicLayout.addView(checkBox);
                }
            else{
                for(int i=0;i<questions.get(position).getCheckBoxes().size();i++){
                    CheckBox checkBox=questions.get(position).getCheckBoxes().get(i);
                    if(SecondPageActivity.onClickState==1 && choices[i][1]==null) checkBox.setTextColor(context.getResources().getColor(R.color.correctAns));
                }
            }
        }
        public void disableCheckBox(){

        }
    }
}
