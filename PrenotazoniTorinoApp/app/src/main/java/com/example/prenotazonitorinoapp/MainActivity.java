package com.example.prenotazonitorinoapp;
import android.annotation.SuppressLint;
import android.os.Bundle;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.prenotazonitorinoapp.ui.guestpage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.prenotazonitorinoapp.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    EditText emailBox, passwordBox,nome, cognome;
    Button  loginbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emailBox = (EditText)findViewById(R.id.username);
        passwordBox = (EditText)findViewById(R.id.password);
        loginbutton = (Button)findViewById(R.id.loginbutton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue=Volley.newRequestQueue(MainActivity.this);
                String sessione2="sessione";
                String URL = "http://192.168.1.54:8080/PrenotazioniTorinoWeb_war_exploded/aut-servlet";
                StringRequest request=new StringRequest(Request.Method.POST, URL,  new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject respObj = new JSONObject(response);
                            String utente = respObj.getString(emailBox.getText().toString());
                            String password = respObj.getString(passwordBox.getText().toString());
                            String sessione = respObj.getString(sessione2);
                        } catch (JSONException e) {
                            System.out.println("valore"+response);
                            e.printStackTrace();
                        }

                        System.out.println("valore " + response);
                        try {
                            JSONObject temp= new JSONObject(response);
                            System.out.println("oggetto: "+temp.get("ruolo"));
                            if(temp.get("ruolo").equals("admin") || temp.get("ruolo").equals("studente") ){
                                binding = ActivityMainBinding.inflate(getLayoutInflater());
                                setContentView(binding.getRoot());

                                BottomNavigationView navView = findViewById(R.id.nav_view);
                                // Passing each menu ID as a set of Ids because each
                                // menu should be considered as top level destinations.
                                AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                                        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                                        .build();
                                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_activity_main);
                                NavigationUI.setupActionBarWithNavController(MainActivity.this, navController, appBarConfiguration);
                                NavigationUI.setupWithNavController(binding.navView, navController);

                                final ListView textView = (ListView)findViewById(R.id.text);
                                RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
                                String URL2 = "http://192.168.1.54:8080/PrenotazioniTorinoWeb_war_exploded/guest-servlet";
                                JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, URL2, null, new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {
                                        final ArrayList<String> listp = new ArrayList<String>();
                                        for (int i = 0; i < response.length(); ++i) {
                                            JSONObject temp = null;
                                            try {
                                                temp = response.getJSONObject(i);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                            try {
                                                assert temp != null;
                                                listp.add(temp.getString("cognome_docente")+ "  "+ temp.getString("corso")+" "+ temp.getString("giorno")+" "+ temp.getInt("ora"));

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }



                                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, listp);
                                        textView.setAdapter(adapter);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("Content-Type", "application/json");

                                        return params;
                                    }
                                };
                                queue.add(request);
                            }else if(temp.get("ruolo").equals("ospite")){
                                Intent ApriGuest = new Intent(MainActivity.this,GuestActivity.class);
                                startActivity(ApriGuest);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                        @Override
                        protected Map<String, String> getParams() {
                        // below line we are creating a map for
                        // storing our values in key and value pair.
                        Map<String, String> params = new HashMap<String, String>();

                        // on below line we are passing our key
                        // and value pair to our parameters.
                        params.put("utente",emailBox.getText().toString());
                        params.put("password", passwordBox.getText().toString());
                        params.put("sessione", "sessione");


                        // at last we are
                        // returning our params.
                        return params;
                    }

                };


                queue.add(request);




            }
        });






    }


}