package com.example.prenotazonitorinoapp;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.prenotazonitorinoapp.ui.home.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Myadapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;

    public Myadapter(ArrayList<String> list, Context context) {
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
            view = inflater.inflate(R.layout.list_custom, null);
        }

        //Handle TextView and display string from your list
        TextView text= (TextView)view.findViewById(R.id.testo);
        text.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button callbtn= (Button)view.findViewById(R.id.btn);

        callbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String sessione;
                String  id= MainActivity.id;
                String stringa=text.getText().toString();
                String[] split = stringa.split("\\s+");
                String id_docente= split[0];
                String id_corso= split[1];
                String giorno= split[5];
                String ora= split[6];
                System.out.println("PRENOTAZIONE " + id_docente+ " " + id_corso);
                RequestQueue queue= Volley.newRequestQueue(context.getApplicationContext());
                StringRequest request= new StringRequest(Request.Method.POST, publicURL.url+"prenota-servlet", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("response: "+ response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("ERRORE PRENOTAZIONE");

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
                        params.put("giorno",giorno);
                        params.put("ora",ora);
                        params.put("sessione", MainActivity.sessione);
                        params.put("android","android");

                        // at last we are
                        // returning our params.
                        return params;
                    }
                };
                queue.add(request);
                System.out.println(MainActivity.sessione);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // yourMethod();
                        refreshFragment();
                    }
                }, 1000);
            }
        });
        return view;
    }

    private void refreshFragment(){
        // This method refreshes the fragment
        NavHostFragment.findNavController(HomeFragment.getAppContext())
                .navigate(R.id.navigation_home);
    }
}
