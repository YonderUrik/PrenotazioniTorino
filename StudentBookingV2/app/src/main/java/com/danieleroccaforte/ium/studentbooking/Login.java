package com.danieleroccaforte.ium.studentbooking;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Login extends AppCompatActivity {
    EditText textInputEditTextEmail, textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignup;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DAO.registerDriver();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextEmail = findViewById(R.id.login_email);
        textInputEditTextPassword = findViewById(R.id.login_password);
        buttonLogin = findViewById(R.id.login_button);
        textViewSignup = findViewById(R.id.signup_text);
        progressBar = findViewById(R.id.login_progress);

        textViewSignup.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignUp.class);
            startActivity(intent);
            finish();
        });

        buttonLogin.setOnClickListener(v -> {
            String email, password;
            email = String.valueOf(textInputEditTextEmail.getText());
            password = String.valueOf(textInputEditTextPassword.getText());

            if(!email.equals("") && !password.equals("")) {
                progressBar.setVisibility(View.VISIBLE);
                //Start ProgressBar first (Set visibility VISIBLE)
                if(DAO.verifyLogin(email, password)){

                    /*
                    passing data[0] from Login to AccountFragment
                     */

                    //changing Context
                    Toast.makeText(getApplicationContext(), "Login avvenuto con successo." , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Email o password errati", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Sono richiesti tutti i campi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}