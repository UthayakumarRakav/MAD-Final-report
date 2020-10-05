package com.example.bus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu extends AppCompatActivity {
    private Button Button1,Button2,Button3,Button4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button1 = (Button)findViewById(R.id.Logout);
        Button2 = (Button)findViewById(R.id.Home);
        Button3 = (Button)findViewById(R.id.Profile);
        Button4 = (Button)findViewById(R.id.Contact);

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(menu.this, login.class);
                startActivity(intent);
            }
        });
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(menu.this, Home.class);
                startActivity(intent);
            }
        });
        Button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(menu.this, ProfileSetting.class);
                startActivity(intent);
            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(menu.this, contact.class);
                startActivity(intent);
            }
        });


    }
}