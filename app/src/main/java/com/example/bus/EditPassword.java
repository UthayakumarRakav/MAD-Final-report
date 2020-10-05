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

public class EditPassword extends AppCompatActivity {

    private Button Button;
    private EditText InputCurrentPassword, InputNewPassword;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        InputCurrentPassword = (EditText) findViewById(R.id.currentpassword);
        InputNewPassword = (EditText) findViewById(R.id.newpassword);
        Button = (Button) findViewById(R.id.button);
        loadingBar = new ProgressDialog(this);


        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                CreateAccount();
            }
        });
    }



    private void CreateAccount()
    {
        String newp = InputNewPassword.getText().toString();
        String current = InputCurrentPassword.getText().toString();
        String A = Prevalent.currentOnlineUser.getPhone();
        if (TextUtils.isEmpty(newp))
        {
            Toast.makeText(this, "Please write your new password...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(current))
        {
            Toast.makeText(this, "Please write current password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Edit password");
            loadingBar.setMessage("Please wait, updating");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(newp, A);
        }
    }



    private void ValidatephoneNumber(final String newp, final String A)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                    HashMap<String, Object> userdataMap = new HashMap<>();
                    userdataMap.put("password", newp);
                    RootRef.child("Users").child(A).updateChildren(userdataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(EditPassword.this, "Congratulations, your account has been updatedd.", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(EditPassword.this, menu.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        Toast.makeText(EditPassword.this, "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
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