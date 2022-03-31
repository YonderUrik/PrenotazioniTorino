package com.example.prenotazonitorinoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.prenotazonitorinoapp.ui.home.HomeFragment;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

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
                String  id= MainActivity.id;
                String id_docente= text.getText().toString().substring(0,1);
                String id_corso= text.getText().toString().substring(2,3);
                String giorno= text.getText().toString().substring(32,39);
                String ora= text.getText().toString().substring(40,42);
                System.out.println("ora:"+ora+"asdas");
                System.out.println("giorno:"+giorno+"dasdas");
                System.out.println("valore id:" + id_docente);
                System.out.println("valore id:" + id_corso);
                System.out.println("id utente:"+id);
                
            }
        });
        

        return view;
    }
}
