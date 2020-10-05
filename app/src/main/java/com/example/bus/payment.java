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
import android.widget.Toast;

import com.example.bus.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class payment extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputCardNumber, InputYearMonth,InputCvv,InputCardName;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        CreateAccountButton = (Button) findViewById(R.id.button);
        InputYearMonth = (EditText) findViewById(R.id.year);
        InputCardNumber = (EditText) findViewById(R.id.Number);
        InputCvv = (EditText) findViewById(R.id.cvv);
        InputCardName = (EditText) findViewById(R.id.card);
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
        String number = InputCardNumber.getText().toString();
        String name = InputCardName.getText().toString();
        String cvv = InputCvv.getText().toString();
        String year = InputYearMonth.getText().toString();
        String A = Prevalent.currentOnlineUser.getPhone();
        if (TextUtils.isEmpty(number))
        {
            Toast.makeText(this, "Please write your card number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write card name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cvv))
        {
            Toast.makeText(this, "Please write card cvv...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(year))
        {
            Toast.makeText(this, "Please write card Year Month...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(number, name, cvv, year, A);
        }
    }



    private void ValidatephoneNumber(final String number, final String name, final String cvv, final String year, final String A)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child("Cards").child(A).exists()))
                {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("CardNo", number);
                    userdataMap.put("CardName", name);
                    userdataMap.put("Cvv", cvv);
                    userdataMap.put("YearMonth", year);
                    RootRef.child("Cards").child(A).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(payment.this, "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(payment.this, contact.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(payment.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(payment.this, "This " + number + " already exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(payment.this, "Please try again using another phone number.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(payment.this, payment.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}