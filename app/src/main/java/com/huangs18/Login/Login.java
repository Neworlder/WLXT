package com.huangs18.Login;

import com.huangs18.Login.HttpClient;
import com.huangs18.Login.AssignmentInfo;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Vector;

public class Login {
    private static String loginAccountSaveUrl = "https://learn.tsinghua.edu.cn/f/loginAccountSave";
    private static String loginPageUrl = "https://id.tsinghua.edu.cn/do/off/ui/auth/login/post/bb5df85216504820be7bba2b0ae1535b/0?/login.do";
    public static Vector run(String userNameStr, String passwordStr) throws Exception {
        HttpClient client = new HttpClient();
//------------------------------------------------------------------------------------
        String loginAccount = "loginAccount=" + userNameStr;
        String response = client.doPost(loginAccountSaveUrl, loginAccount);
        //System.out.println(response + '\n');
//------------------------------------------------------------------------------------
        String loginInfo = "i_user=" + userNameStr + "&i_pass=" + passwordStr + "&atOnce=true";
        response = client.doPost(loginPageUrl, loginInfo);
        //System.out.println(response + '\n');
        Matcher matcher = Pattern.compile("ticket=(.*)\"").matcher(response);
        String ticket = "";
        if (matcher.find()) {
            ticket = matcher.group(1);
        } else {
            //System.out.println("Fake password!");
            throw(new Exception("用户名或密码错误！"));
        }
//------------------------------------------------------------------------------------
        String ticketUrl = "https://learn.tsinghua.edu.cn/f/login.do?status=SUCCESS&ticket=" + ticket;
        response = client.doGet(ticketUrl);
        //System.out.println(response + '\n');
//------------------------------------------------------------------------------------
        String ticketUrl1 = "https://learn.tsinghua.edu.cn/b/j_spring_security_thauth_roaming_entry?ticket=" + ticket;
        response = client.doGet(ticketUrl1);
        //System.out.println(response + '\n');
//------------------------------------------------------------------------------------
        String studentUrl = "https://learn.tsinghua.edu.cn/f/wlxt/index/course/student/";
        response = client.doGet(studentUrl);
        //System.out.println(response + '\n');
//------------------------------------------------------------------------------------
        String semesterUrl = "https://learn.tsinghua.edu.cn/b/kc/zhjw_v_code_xnxq/getCurrentAndNextSemester";
        response = client.doGet(semesterUrl);
        //System.out.println(response + '\n');
//------------------------------------------------------------------------------------
        String courseUrl = "https://learn.tsinghua.edu.cn/b/wlxt/kc/v_wlkc_xs_xkb_kcb_extend/student/loadCourseBySemesterId/2019-2020-2";
        response = client.doGet(courseUrl);
        //System.out.println(response + '\n');
        Matcher matcher1 = Pattern.compile("\\[(.*)\\]").matcher(response);
        String courseString = "";
        if (matcher1.find()) {
            courseString = matcher1.group(1);
        }
        //System.out.println(response);
        String[] courses = courseString.split("\\}");
        Vector<AssignmentInfo> assigninfo = new Vector<AssignmentInfo>(200);
        for (String course : courses) {
            //System.out.println(course);
            String wjzys = "";
            String id = "";
            Matcher matcher2 = Pattern.compile("\"wjzys\":([0-9][0-9]?),").matcher(course);
            if (matcher2.find()) {
                wjzys = matcher2.group(1);
            }
            Matcher matcher3 = Pattern.compile("\"wlkcid\":\"(.*?)\"").matcher(course);
            if (matcher3.find()) {
                id = matcher3.group(1);
            }
            // 课程名称 kcmStr
            String kcmStr = "";
            Matcher kcmMatcher = Pattern.compile("\"kcm\":\"(.*?)\"").matcher(course);
            if (kcmMatcher.find()) {
                kcmStr = kcmMatcher.group(1);
            }
            //System.out.println(kcmStr);
            if (!wjzys.equals("0")) {
                String wjzyUrl = "https://learn.tsinghua.edu.cn/b/wlxt/kczy/zy/student/index/zyListWj?wlkcid=" + id + "&size=4";
                response = client.doGet(wjzyUrl);
                //System.out.println(response);
                // 每个科目有多个未交作业
                String zybt = "";
                Matcher btmatcher = Pattern.compile("\"bt\":\"(.*?)\"").matcher(response);
                while (btmatcher.find()) {
                    zybt = btmatcher.group(1);
                    // 截止日期和作业一一对应
                    String jzsj = "";
                    Matcher timeMatcher = Pattern.compile("\"jzsjStr\":\"(.*?)\"").matcher(response);
                    if(timeMatcher.find()) {
                        jzsj = timeMatcher.group(1);
                    }
                    assigninfo.add(new AssignmentInfo(kcmStr, zybt, jzsj));
                }
            }
        }
//------------------------------------------------------------------------------------
        // sort according to DDL.
        for(int i = 0; i < assigninfo.size(); i++) {
            for(int j = 0; j < assigninfo.size() - i - 1; j++) {
                if(AssignmentInfo.before(assigninfo.get(j + 1), assigninfo.get(j))) {
                    AssignmentInfo tmp = assigninfo.get(j);
                    assigninfo.set(j, assigninfo.get(j + 1));
                    assigninfo.set(j + 1, tmp);
                }
            }
        }
        return assigninfo;
    }
}