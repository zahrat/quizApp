package com.test.quiz.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.quiz.Model.Exam;
import com.test.quiz.R;
import com.test.quiz.SecondPageActivity;

import java.util.List;


public class MainListAdapter extends ArrayAdapter<Exam> {
    List<Exam> exams;
    public MainListAdapter(List<Exam> data, Context context) {
        super(context, R.layout.mainmenu_item,data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final Exam dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        Holder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.mainmenu_item, parent, false);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
            result=convertView;
        }
        viewHolder.textView.setText("Session "+dataModel.getExamNo());
        if(dataModel.getIsActive()==1)
            viewHolder.imageView.setImageDrawable(parent.getContext().getResources().getDrawable(R.drawable.unlock));
        else {
            viewHolder.imageView.setImageDrawable(parent.getContext().getResources().getDrawable(R.drawable.lock));
            viewHolder.textView.setEnabled(false);
        }
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(parent.getContext(), SecondPageActivity.class);
                intent.putExtra("sessionNo",dataModel.getExamNo());
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    public static class Holder{
        ImageView imageView;
        TextView textView;
    }
}
