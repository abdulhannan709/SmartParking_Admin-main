package com.example.smartparking_admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email_txt;
    EditText password_txt;
    Button signin_btn;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_txt = findViewById(R.id.txtemail_login);
        password_txt = findViewById(R.id.txtpassword_login);
        signin_btn = findViewById(R.id.btnlogin);

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogInrenter();
            }
        });
    }

    private void LogInrenter() {

        final ProgressDialog loading = ProgressDialog.show(this, "Signing In", "Please wait ...");
        loading.setCancelable(false);

        String email = email_txt.getText().toString().trim();
        String password = password_txt.getText().toString().trim();

        mauth = FirebaseAuth.getInstance();

        mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    FirebaseUser user = mauth.getCurrentUser();
                    Intent i = new Intent(LoginActivity.this, AdminMain.class);
                    startActivity(i);
                    loading.dismiss();
                } else {

                    loading.dismiss();
                    Toast.makeText(LoginActivity.this, "SignIn failed.", Toast.LENGTH_SHORT).show();

                }

            }
        });


    }
}