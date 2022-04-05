package com.example.prenotazonitorinoapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.prenotazonitorinoapp.ui.guestpage;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuestActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest);
        System.out.println("ci sono");
        final ListView textView = (ListView)findViewById(R.id.text);
        RequestQueue queue= Volley.newRequestQueue(GuestActivity.this);
        JsonArrayRequest request=new JsonArrayRequest(Request.Method.GET, publicURL.url+"guest-servlet", null, new Response.Listener<JSONArray>() {
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



                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(GuestActivity.this, android.R.layout.simple_expandable_list_item_1, listp);
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
    }

}