package com.huangs18.Layouts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HomeworkLayout extends RelativeLayout {
    TextView courseName;
    TextView homeworkTitle;
    TextView DDL;
    public HomeworkLayout(Context context, String coursename, String homework, String date) {
        super(context);
        setBackgroundColor(Color.rgb(153,51,250));
        LayoutParams layparams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layparams.setMargins(3,5,3,5);
        setLayoutParams(layparams);
        courseName = new TextView(context);
        courseName.setTextColor(Color.rgb(255,255,255));
        courseName.setTextSize(20);
        courseName.setText("课程："+ coursename);
        courseName.setId(1);
        LayoutParams courseParam = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        courseParam.setMargins(15, 15,15,15);
        courseName.setLayoutParams(courseParam);

        homeworkTitle = new TextView(context);
        homeworkTitle.setTextSize(15);
        homeworkTitle.setTextColor(Color.rgb(255,255,255));
        homeworkTitle.setText(homework);
        LayoutParams hwParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        hwParam.setMargins(15,15,15,25);
        hwParam.addRule(BELOW, courseName.getId());
        homeworkTitle.setLayoutParams(hwParam);
        homeworkTitle.setId(2);

        DDL = new TextView(context);
        DDL.setTextSize(15);
        DDL.setTextColor(Color.rgb(255,128,128));
        DDL.setText("DDL：" + date);
        LayoutParams ddlParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //ddlParam.addRule(RIGHT_OF, homeworkTitle.getId());
        ddlParam.setMargins(15,15,25,25);
        ddlParam.addRule(ALIGN_PARENT_RIGHT);
        ddlParam.addRule(BELOW, courseName.getId());
        DDL.setLayoutParams(ddlParam);

        addView(courseName);
        addView(homeworkTitle);
        addView(DDL);
    }
}
