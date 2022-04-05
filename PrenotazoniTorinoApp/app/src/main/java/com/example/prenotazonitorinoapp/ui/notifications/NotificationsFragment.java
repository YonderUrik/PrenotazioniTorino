package com.example.prenotazonitorinoapp.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prenotazonitorinoapp.MainActivity;
import com.example.prenotazonitorinoapp.Myadapter;
import com.example.prenotazonitorinoapp.R;
import com.example.prenotazonitorinoapp.adapter2;
import com.example.prenotazonitorinoapp.databinding.FragmentNotificationsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        final ListView List = binding.prenotazionieffettuate;
        String sessione2= "sessione";
        String id2=MainActivity.id;
        RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
        String URL2 = "http://192.168.1.54:8080/PrenotazioniTorinoWeb_war_exploded/ripetizioni-prenotate-servlet";
        StringRequest request= new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final ArrayList<String> listp = new ArrayList<String>();
                JSONArray temp=null;
                try {

                    temp= new JSONArray(response);
                    for(int i=0;i<temp.length();i++){
                        listp.add(temp.getJSONObject(i).getString("idDocente")+" "+temp.getJSONObject(i).getString("idCorso")+" "+temp.getJSONObject(i).getString("docente")+" "+ temp.getJSONObject(i).getString("corso")+" "+temp.getJSONObject(i).getString("data")+" "+temp.getJSONObject(i).getInt("ora")+" "+temp.getJSONObject(i).getString("stato"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_expandable_list_item_1, listp);
                List.setAdapter(new adapter2(listp,getActivity().getApplicationContext()));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("qualcosa non va");
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("id", id2);
                params.put("sessione", sessione2);
                params.put("android", "android");


                // at last we are
                // returning our params.
                return params;
            }
        };
        queue.add(request);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}