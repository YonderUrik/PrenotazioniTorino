package com.danieleroccaforte.ium.studentbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import entry.path;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button newUserButton = findViewById(R.id.signup_button);

        final EditText nameInput = findViewById(R.id.signup_name);
        final EditText surnameInput = findViewById(R.id.signup_surname);
        final EditText emailInput = findViewById(R.id.signup_email);
        final EditText passwordInput = findViewById(R.id.signup_password);

        newUserButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {

                    RequestParams newUser = new RequestParams();
                    newUser.add("urldiprevenienza","android");

                    JSONObject object = new JSONObject();
                    try {

                        object.put("name", nameInput.getText().toString() );
                        object.put("surname", surnameInput.getText().toString() );
                        object.put("email", emailInput.getText().toString() );
                        object.put("password", passwordInput.getText().toString() );

                        newUser.put("file", object);

                        String paginaURL = path.GET() + "/IUM_server/NewUser";

                        new AsyncHttpClient().post(paginaURL, newUser, new JsonHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                JSONObject JsonObj = response;

                                try {
                                    JSONObject reply = (JSONObject) JsonObj.get("reply");

                                    if ( reply.get("state").equals("Match") ) {
                                        Intent intent = new Intent(SignUp.this, MainActivity.class);
                                        startActivity(intent);
                                    }

                                    Toast.makeText(SignUp.this,"LogIn Success ", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) { e.printStackTrace(); }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                                Toast.makeText(SignUp.this,"Error" + throwable, Toast.LENGTH_SHORT).show();
                            }

                        });

                        System.out.println("OK");

                    } catch (Exception e) {
                        Toast.makeText(SignUp.this,"Error1", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    Toast.makeText(SignUp.this,"Error2", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

        });

        Button loginButton = findViewById(R.id.signup_button);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, MainActivity.class);
                startActivity(intent);
            }

        });

    }

}
