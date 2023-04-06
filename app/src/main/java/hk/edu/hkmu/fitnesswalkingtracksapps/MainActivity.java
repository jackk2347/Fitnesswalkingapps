package hk.edu.hkmu.fitnesswalkingtracksapps;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;


import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    private HttpURLConnection connection ;
    private URL url  = null ;
    private String jsonurl = "https://www.lcsd.gov.hk/datagovhk/facility/facility-fw.json";
    private StringBuffer buffer = new StringBuffer();
    private ListView mListView ;
    private DataListAdapter adapter ;
    private ArrayList<Data> Datalist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mListView = (ListView) findViewById(R.id.listView);
        BufferedReader reader = null;
        InputStream inputStream = null ;

        try {
            url = new URL(jsonurl);
            connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            if (buffer != null && buffer.length()!= 0 ){
                String content = buffer.toString();
                JSONArray jsonarray = new JSONArray(content);
                for(int i = 0 ; i < jsonarray.length() ; i ++){
                    JSONObject data = jsonarray.getJSONObject(i);
                    String title = "Title : " + data.getString("Title_en").toString();
                    String distinct = "District : " + data.getString("District_en").toString();
                    String Route = "Route : " + data.getString("Route_en").toString();
                    String Access = "Access : " + data.getString("HowToAccess_en").toString();
                    String MapURL_en = data.getString("MapURL_en").toString() ;
                    String Latitude = data.getString("Latitude").toString();
                    String Longitude = data.getString("Longitude").toString();
                    Route = Route.replaceAll("<br>","");
                    Access = Access.replaceAll("<br>","");
                    Latitude = Latitude.replaceAll(",","");
                    Longitude = Longitude.replaceAll(",","");
                    Data datas = new Data(title,distinct,Route,Access,MapURL_en,Latitude,Longitude);
                    Datalist.add(datas);

                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally{
            connection.disconnect();
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DataListAdapter adapter = new DataListAdapter(this,R.layout.adapter_view_layout,Datalist);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String diagmessage = (CharSequence) Datalist.get(position).getTitle() + "\n" + (CharSequence) Datalist.get(position).getDistrict() + "\n" + (CharSequence) Datalist.get(position).getRoute() + "\n" + (CharSequence) Datalist.get(position).getAccessway()  + "\nLatitude : " + Datalist.get(position).getLatitude() + "\nLongitude : " + Datalist.get(position).getLongitude() ;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(Html.fromHtml("<font color = '#E91E63'>The information : </font>"));
                builder.setMessage( diagmessage );
                builder.setCancelable(false);

                builder.setNeutralButton("Search GPS ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String googlegpsurl = "https://www.google.com/maps/search/?api=1&query=" + Datalist.get(position).getLatitude() + "%2C" + Datalist.get(position).getLongitude();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(googlegpsurl)));

                    }
                });

                builder.setNegativeButton("Map Photo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Datalist.get(position).getMapurl())));

                    }
                });


                builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }


}