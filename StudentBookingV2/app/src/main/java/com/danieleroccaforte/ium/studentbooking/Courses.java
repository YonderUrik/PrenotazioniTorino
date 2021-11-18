package com.danieleroccaforte.ium.studentbooking;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import entry.path;

public class Courses extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.courses_fragment);
        Button newActivityButton = findViewById(R.id.newActivityButton);
        final ListView activityListView = findViewById(R.id.activityListView);
        TextView homeText = findViewById(R.id.homeText);

        String user = "";
        Intent intent = getIntent();
        try { user = new String(intent.getStringExtra("EXTRA_SESSION_user"));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Courses.this, "Error data\n Try to reload the page."+ user + "...", Toast.LENGTH_SHORT).show();
        }

        homeText.setText("Hello "+user);


        final String finalUser1 = user;
        newActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Courses.this, searchPage.class);
                intent.putExtra("EXTRA_SESSION_user", finalUser1);
                startActivity(intent);
            }
        });


        String paginaURL = path.GET() + "/IUM_server/getActivity";

        RequestParams srch = new RequestParams();
        JSONObject object = new JSONObject();

        srch.add("urldiprevenienza", "android");
        try { object.put("user", user );
        } catch (JSONException e) { e.printStackTrace(); }
        srch.put("file", object);


        try {
            final String finalUser = user;
            new AsyncHttpClient().post(paginaURL, srch, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    JSONObject JsonObj = response;


                    String[] text = new String[0];
                    try {
                        text = new String[ JsonObj.getJSONObject("reply").length() ];
                        String[] yprof = new String[ JsonObj.getJSONObject("reply").length() ];
                        String[] ymateria = new String[ JsonObj.getJSONObject("reply").length() ];
                        String[] date = new String[ JsonObj.getJSONObject("reply").length() ];
                        String[] luogo_ripetizione = new String[ JsonObj.getJSONObject("reply").length() ];
                        int[] ybooking = new int[ JsonObj.getJSONObject("reply").length() ];


                        try {
                            for ( int i=0; i<JsonObj.getJSONObject("reply").length(); i++) {

                                JSONObject activity = (JSONObject) JsonObj.getJSONObject("reply").getJSONObject("activity_"+i);


                                System.out.println("pop: " + activity);


                                yprof[i] = (String) activity.get("profname");
                                ymateria[i] = (String) activity.get("materia");
                                date[i] = (String) activity.get("date");
                                luogo_ripetizione[i] = (String) activity.get("luogo_ripetizione");
                                ybooking[i] = Integer.parseInt((String) activity.get("state"));

                                text[i] = "Lesson with prof :'" + yprof[i] + "'.\nOn "+ date[i] +".\n" + luogo_ripetizione[i] + " .";
                            }

                        } catch (Exception e) { e.printStackTrace(); Toast.makeText(Courses.this,"Error no connection", Toast.LENGTH_SHORT).show(); }

                        rowAdapter adapter = new rowAdapter(Courses.this, text, yprof, ymateria, date, luogo_ripetizione, ybooking, finalUser);
                        activityListView.setAdapter(adapter);

                    } catch (JSONException e) { e.printStackTrace(); }

                    TextView noactivityText = findViewById(R.id.noactivityText);

                    try { if (JsonObj.getJSONObject("reply").length() > 0) noactivityText.setVisibility(View.INVISIBLE);
                    } catch (JSONException e) { e.printStackTrace(); }
                }
            });

        } catch (Exception e) { e.printStackTrace(); Toast.makeText(Courses.this,"Error no connection", Toast.LENGTH_SHORT).show(); }
    }


    class rowAdapter extends ArrayAdapter<String> {

        HashMap<String,Integer> mouthY = new HashMap<String,Integer>();

        private final Context context;
        private String[] text;
        private String[] yprof;
        private String[] ymateria;
        private String[] ydate;
        private String[] yluogo_ripetizione;
        private int[] ybooking;
        private String yuser;

        public rowAdapter(Context c, String[] text, String[] yprof, String[] ymateria, String[] date, String[] luogo_ripetizione, int[] ybooking, String yuser) {
            super(c, R.layout.rowbooking,text);

            this.context = c;
            this.text = text;
            this.yprof = yprof;
            this.ymateria = ymateria;
            this.ydate = date;
            this.yluogo_ripetizione = luogo_ripetizione;
            this.ybooking = ybooking;
            this.yuser = yuser;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.rowactivity, parent, false);
            View rowB = layoutInflater.inflate(R.layout.rowbooking, parent, false);



            // controllo bottone enable
            if (ybooking[position] == 1) {

                final TextView myActivity = row.findViewById(R.id.textActivity);
                myActivity.setText(text[position]);

                final Button button1 = row.findViewById(R.id.buttonActivity1);
                final Button didButton = row.findViewById(R.id.buttonActivity2);


                Calendar dataOgg = Calendar.getInstance();

                String[] dd = ydate[position].split("/");
                Calendar dataLess = new GregorianCalendar(Integer.parseInt(dd[0]),Integer.parseInt(dd[1]),Integer.parseInt(dd[2]),Integer.parseInt(dd[3]),0,0);

                System.out.println("DataOgg: " + dataOgg.getTime() + "\nDataLesson: " + dataLess.getTime() + "\nprima ? " + dataOgg.before(dataLess) );

                if ( dataOgg.before(dataLess) ) {     // prima della lezione

                    // rinunciare ad una lezione
                    button1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String paginaURL = path.GET() + "/IUM_server/booking";

                            RequestParams srch = new RequestParams();
                            JSONObject object = new JSONObject();

                            srch.add("urldiprevenienza","android");

                            try {
                                object.put("user", yuser);
                                object.put("profname", yprof[position]);
                                object.put("materia", ymateria[position]);
                                object.put("dataW",ydate[position]);

                                object.put("setvalue", -1);

                                srch.put("file", object);

                                System.out.println(object.toString());

                                new AsyncHttpClient().post(paginaURL, srch, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);

                                        Toast.makeText(Courses.this,"Gave up the lesson: success", Toast.LENGTH_SHORT).show();

                                        myActivity.setText("Activity deleted");
                                        didButton.setEnabled(false);
                                        button1.setEnabled(false);
                                        didButton.setText("---");
                                        button1.setText("---");
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                        super.onFailure(statusCode, headers, responseString, throwable);
                                        Toast.makeText(Courses.this,"Error: " + throwable, Toast.LENGTH_SHORT).show();
                                    }

                                });
                            } catch (JSONException e) { e.printStackTrace(); Toast.makeText(Courses.this,"Error: ", Toast.LENGTH_SHORT).show(); }
                        }
                    });

                    button1.setText("X");
                    button1.setBackgroundColor(Color.RED);

                    didButton.setEnabled(false);
                    didButton.setBackgroundColor(Color.GREEN);
                    didButton.setText("Book");
                    didButton.setTextColor(Color.BLACK);

                } else {        // dopo la lezione

                    // per settare come non partecipata una lezione
                    didButton.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String paginaURL = path.GET() + "/IUM_server/booking";

                            RequestParams srch = new RequestParams();
                            JSONObject object = new JSONObject();

                            srch.add("urldiprevenienza","android");

                            try {

                                object.put("user", yuser);
                                object.put("profname", yprof[position]);
                                object.put("materia", ymateria[position]);
                                object.put("dataW",ydate[position]);

                                object.put("setvalue", -1 );

                                srch.put("file", object);


                                new AsyncHttpClient().post(paginaURL, srch, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);

                                        JSONObject JsonObj = response;

                                        try {
                                            JSONObject reply = (JSONObject) JsonObj.get("reply");
                                            Toast.makeText(Courses.this,"Success ",Toast.LENGTH_SHORT).show();
                                            didButton.setEnabled(false);

                                        } catch (JSONException e) { e.printStackTrace(); Toast.makeText(Courses.this,"Server error",Toast.LENGTH_SHORT).show(); }

                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                        super.onFailure(statusCode, headers, responseString, throwable);
                                        Toast.makeText(Courses.this,"Error: " + throwable, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } catch (JSONException e) { e.printStackTrace(); Toast.makeText(Courses.this,"Error: ", Toast.LENGTH_SHORT).show(); }
                        }
                    });



                    // per settare come partecipata una lezione
                    didButton.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String paginaURL = path.GET() + "/IUM_server/booking";

                            RequestParams srch = new RequestParams();
                            JSONObject object = new JSONObject();

                            srch.add("urldiprevenienza","android");

                            try {
                                object.put("user", yuser);
                                object.put("profname", yprof[position]);
                                object.put("materia", ymateria[position]);
                                object.put("dataW",ydate[position]);

                                object.put("setvalue", 2 );

                                srch.put("file", object);


                                new AsyncHttpClient().post(paginaURL, srch, new JsonHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);

                                        Toast.makeText(Courses.this,"do", Toast.LENGTH_SHORT).show();

                                        myActivity.setText("Activity deleted");
                                        didButton.setEnabled(false);
                                        button1.setEnabled(false);
                                        didButton.setText("---");
                                        button1.setText("---");
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                        super.onFailure(statusCode, headers, responseString, throwable);
                                        Toast.makeText(Courses.this,"Error: " + throwable, Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } catch (JSONException e) { e.printStackTrace(); Toast.makeText(Courses.this,"Error: ", Toast.LENGTH_SHORT).show(); }
                        }
                    });


                    button1.setBackgroundColor(Color.RED);
                    button1.setText("X");
                    didButton.setBackgroundColor(Color.GREEN);

                }


            } else {
                // caso che non sia uno stato definito

                final TextView myActivity = rowB.findViewById(R.id.textActivity);
                myActivity.setText(text[position]);

                final TextView bookingState = rowB.findViewById(R.id.BookingState);

                Map<Integer, String> mp = new HashMap<>();
                mp.put(-1, "rinunciata");
                mp.put(0, "seguita");
                mp.put(2, "non partecipata");
                mp.put(1, "ERROR");

                Map<Integer, Integer> cmp = new HashMap<>();
                cmp.put(-1, Color.rgb(255, 153, 0 ));
                cmp.put(0, Color.rgb(0, 204, 0));
                cmp.put(2, Color.rgb(204, 0, 0));
                cmp.put(1, Color.rgb(204, 0, 0));


                bookingState.setText(mp.get(ybooking[position]));
                bookingState.setTextColor(cmp.get(ybooking[position]));

            }

            return ( (ybooking[position] == 1) ? row : rowB);
        }
    }
}