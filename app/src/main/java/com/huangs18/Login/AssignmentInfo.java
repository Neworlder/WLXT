package com.huangs18.Login;

class DateInfo {
    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;
    public DateInfo(String time) {
        try {
            String[] info = time.split(" ");
            String[] info0 = info[0].split("-");
            String[] info1 = info[1].split(":");
            year = Integer.parseInt(info0[0]);
            month = Integer.parseInt(info0[1]);
            day = Integer.parseInt(info0[2]);
            hour = Integer.parseInt(info1[0]);
            minute = Integer.parseInt(info1[1]);
        }
        catch (ArrayIndexOutOfBoundsException exception) {
            System.out.println("Date infomation format wrong!");
        }
    }
    public static boolean before(DateInfo d1, DateInfo d2) {
        if(d1.year < d2.year)   return true;
        if(d1.year > d2.year)   return false;
        if(d1.month < d2.month) return true;
        if(d1.month > d2.month) return false;
        if(d1.day < d2.day) return true;
        if(d1.day > d2.day) return false;
        if(d1.hour < d2.hour)   return true;
        if(d1.hour > d2.hour)   return false;
        if(d1.minute < d2.minute)   return true;
        return false;
    }
}

public class AssignmentInfo {
    public String courseName;
    public String assignmentTitle;
    public DateInfo ddl;
    public String date;
    public AssignmentInfo(String courseName_, String assignmentTitle_, String ddl_) {
        courseName = new String(courseName_);
        assignmentTitle = new String(assignmentTitle_);
        ddl = new DateInfo(ddl_);
        date = new String(ddl_);
    }
    public static boolean before(AssignmentInfo a1, AssignmentInfo a2) {
        return DateInfo.before(a1.ddl, a2.ddl);
    }
}
