package com.danieleroccaforte.ium.studentbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    EditText textInputEditTextName, textInputEditTextSurname, textInputEditTextEmail, textInputEditTextPassword;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputEditTextName = findViewById(R.id.signup_name);
        textInputEditTextSurname = findViewById(R.id.signup_surname);
        textInputEditTextEmail = findViewById(R.id.signup_email);
        textInputEditTextPassword = findViewById(R.id.signup_password);
        buttonSignUp = findViewById(R.id.signup_button);
        textViewLogin = findViewById(R.id.login_text);
        progressBar = findViewById(R.id.signup_progress);

        textViewLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        });

        buttonSignUp.setOnClickListener(v -> {
            String name, surname, email, password;
            name = String.valueOf(textInputEditTextName.getText());
            surname = String.valueOf(textInputEditTextSurname.getText());
            email = String.valueOf(textInputEditTextEmail.getText());
            password = String.valueOf(textInputEditTextPassword.getText());

            if(!name.equals("") && !surname.equals("") && !email.equals("") && !password.equals("")) {
                progressBar.setVisibility(View.VISIBLE);
                //Start ProgressBar first (Set visibility VISIBLE)
                DAO.registerDriver();
                progressBar.setVisibility(View.GONE);
                //End ProgressBar (Set visibility to GONE)
                if(DAO.checkedSignup(email, password)){
                    DAO.insertSignup(name, surname, email, password);
                    Toast.makeText(getApplicationContext(), "Utente registrato con successo.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"Utente già registrato.", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Sono richiesti tutti i campi", Toast.LENGTH_SHORT).show();
            }
        });



    }
}