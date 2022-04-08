package com.example.prenotazonitorinoapp.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.prenotazonitorinoapp.GuestActivity;
import com.example.prenotazonitorinoapp.MainActivity;
import com.example.prenotazonitorinoapp.Myadapter;
import com.example.prenotazonitorinoapp.R;
import com.example.prenotazonitorinoapp.databinding.FragmentHomeBinding;
import com.example.prenotazonitorinoapp.publicURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeFragment extends Fragment {
    public int idcorso, iddocente;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private static Fragment fragment;
    Button prenota;
    final ArrayList<String> listid = new ArrayList<String>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment = HomeFragment.this;
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final ListView List = binding.text;
        extracted(List);


        /* continuare qui */


        return root;
    }

    public void extracted(ListView List) {
        RequestQueue queue= Volley.newRequestQueue(getActivity().getApplicationContext());
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
                        listp.add(temp.getString("id_docente")+" "+temp.getString("id_corso")+" "+temp.getString("cognome_docente")+ "  "+ temp.getString("corso")+" "+ temp.getString("giorno")+" "+ temp.getInt("ora")+" ");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                System.out.println("listp: "+ listp);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_expandable_list_item_1, listp);
                List.setAdapter(new Myadapter(listp,getActivity().getApplicationContext()));
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

    public static Fragment getAppContext() {
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}