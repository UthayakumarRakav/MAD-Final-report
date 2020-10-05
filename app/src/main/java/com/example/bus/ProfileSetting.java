package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.bus.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class ProfileSetting extends AppCompatActivity {

    private Button CreateAccountButton,EditPassword;
    private EditText InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;
    private String A;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        CreateAccountButton = (Button) findViewById(R.id.button2);
        EditPassword = (Button) findViewById(R.id.button1);
        InputPassword = (EditText) findViewById(R.id.Password);
        InputPhoneNumber = (EditText) findViewById(R.id.PhoneNo);
        loadingBar = new ProgressDialog(this);
        final DatabaseReference RootRef,RootRef1,RootRef2,RootRef3;
        RootRef= FirebaseDatabase.getInstance().getReference("Users");
        RootRef1= FirebaseDatabase.getInstance().getReference("Contact");
        RootRef2= FirebaseDatabase.getInstance().getReference("Cards");
        RootRef3= FirebaseDatabase.getInstance().getReference("Book");

        InputPhoneNumber.setText(Prevalent.currentOnlineUser.getPhone());
        InputPassword.setText(Prevalent.currentOnlineUser.getPassword());
        A = Prevalent.currentOnlineUser.getPhone();
        EditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileSetting.this, EditPassword.class);
                startActivity(intent);
            }
        });
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RootRef.child(A).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProfileSetting.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ProfileSetting.this, login
                                    .class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ProfileSetting.this, "Something went Wrong, check your connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                RootRef1.child(A).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        } else {

                        }
                    }
                });
                RootRef2.child(A).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        } else {

                        }
                    }
                });
                RootRef3.child(A).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        } else {

                        }
                    }
                });

            }
        });
    }
}