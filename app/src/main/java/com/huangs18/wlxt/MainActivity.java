package com.huangs18.wlxt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginButton = findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = ((EditText)findViewById(R.id.Username)).getText().toString();
                String pwd = ((EditText)findViewById(R.id.Password)).getText().toString();
                if (true) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("userName", userName);
                    bundle.putCharSequence("password", pwd);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}