package com.example.bus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bus.Model.Users;
import com.example.bus.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Home extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputFrom, InputTo,InputDate;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        CreateAccountButton = (Button) findViewById(R.id.button);
        InputFrom= (EditText) findViewById(R.id.from);
        InputTo = (EditText) findViewById(R.id.to);
        InputDate = (EditText) findViewById(R.id.date);
        loadingBar = new ProgressDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();
            }
        });
    }



    private void CreateAccount()
    {
        String From = InputFrom.getText().toString();
        String To = InputTo.getText().toString();
        String Date = InputDate.getText().toString();
        String A =Prevalent.currentOnlineUser.getPhone();
        if (TextUtils.isEmpty(From))
        {
            Toast.makeText(this, "Please write from...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(To))
        {
            Toast.makeText(this, "Please write to...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Date))
        {
            Toast.makeText(this, "Please write Date...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(From, To, Date, A);
        }
    }



    private void ValidatephoneNumber(final String From, final String To, final String Date, final String A)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("From", From);
                    userdataMap.put("To", To);
                    userdataMap.put("Date", Date);
                    RootRef.child("Book").child(A).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(Home.this, "Your details Saved.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(Home.this, payment.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(Home.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}