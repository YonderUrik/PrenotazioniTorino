package com.example.prenotazonitorinoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prenotazonitorinoapp.ui.home.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapter2 extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    public adapter2(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_custom2, null);
        }

        //Handle TextView and display string from your list
        TextView text= (TextView)view.findViewById(R.id.testo2);
        text.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button conferma= (Button)view.findViewById(R.id.conferma);
        Button disdici=(Button)view.findViewById(R.id.disdici);
        String URL2 = "http://172.21.32.145:8080/PrenotazioniTorinoWeb_war_exploded/gestione-servlet";
        conferma.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("conferma");
                String  id= MainActivity.id;
                String stringa=text.getText().toString();
                String[] split = stringa.split("\\s+");

                String id_docente=split[0];
                String id_corso= split[1];
                String giorno= split[5];
                String ora= split[6];
                System.out.println(stringa);
                System.out.println("id utente: "+id+ " docente: " + id_docente+" corso: "+ id_corso);
                System.out.println("ora: "+ ora);
                System.out.println("giorno: "+ giorno);

                RequestQueue queue= Volley.newRequestQueue(context.getApplicationContext());
                StringRequest request= new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response: "+ response);
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
                        params.put("id",id);
                        params.put("id_docente",id_docente);
                        params.put("id_corso",id_corso);
                        params.put("data",giorno);
                        params.put("ora",ora);
                        params.put("sessione", MainActivity.sessione);
                        params.put("android","android");
                        params.put("post","conferma");



                        // at last we are
                        // returning our params.
                        return params;
                    }
                };
                queue.add(request);
            }
        });


        disdici.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("disdici");
                String  id= MainActivity.id;
                String stringa=text.getText().toString();
                String[] split = stringa.split("\\s+");

                String id_docente=split[0];
                String id_corso= split[1];
                String giorno= split[5];
                String ora= split[6];
                System.out.println(stringa);
                System.out.println("id utente: "+id+ " docente: " + id_docente+" corso: "+ id_corso);
                System.out.println("ora: "+ ora);
                System.out.println("giorno: "+ giorno);
                RequestQueue queue= Volley.newRequestQueue(context.getApplicationContext());
                StringRequest request= new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response: "+ response);
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
                        params.put("id",id);
                        params.put("id_docente",id_docente);
                        params.put("id_corso",id_corso);
                        params.put("data",giorno);
                        params.put("ora",ora);
                        params.put("sessione", MainActivity.sessione);
                        params.put("android","android");
                        params.put("post","disdici");



                        // at last we are
                        // returning our params.
                        return params;
                    }
                };
                queue.add(request);

            }
        });


        return view;
    }
}