package com.danieleroccaforte.ium.studentbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import entry.path;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText EmailTextBox = findViewById(R.id.login_email);
        final EditText PasswordTextBox = findViewById(R.id.login_password);

        Button logInButton = findViewById(R.id.login_button);
        logInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {

                    RequestParams user = new RequestParams();
                    user.add("urldiprevenienza","android");

                    JSONObject object = new JSONObject();
                    try {
                        object.put("username", EmailTextBox.getText().toString() );
                        object.put("password", PasswordTextBox.getText().toString() );

                        user.put("file", object);

                        String paginaURL = path.GET() + "/IUM_server/login"; //VEDERE

                        new AsyncHttpClient().post(paginaURL, user, new JsonHttpResponseHandler() {

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                JSONObject JsonObj = response;

                                try {
                                    JSONObject reply = (JSONObject) JsonObj.get("reply");

                                    if ( reply.get("state").equals("Match") ) {
                                        Toast.makeText(MainActivity.this,"LogIn Success "+EmailTextBox.getText().toString(), Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(MainActivity.this, Courses.class);

                                        intent.putExtra("EXTRA_SESSION_user", EmailTextBox.getText().toString());
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(MainActivity.this,"User not found\n Controll username e password", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) { e.printStackTrace(); }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                super.onFailure(statusCode, headers, responseString, throwable);
                                Toast.makeText(MainActivity.this,"server Error", Toast.LENGTH_SHORT).show();
                            }

                        });

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this,"Error1", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,"Error2", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }

        });


        Button newUserButton = findViewById(R.id.signup_button);
        newUserButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }

        });

    }
}