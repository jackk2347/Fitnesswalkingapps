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

public class DisplayActivity extends AppCompatActivity {
    private HttpURLConnection connection;
    private URL url = null;
    private String jsonurl = "https://www.lcsd.gov.hk/datagovhk/facility/facility-fw.json";
    private StringBuffer buffer = new StringBuffer();
    private ListView mListView;
    private DataListAdapter adapter;
    private ArrayList<Data> Datalist = new ArrayList<>();
    private String title, distinct, Access, MapURL, Latitude, Longitude, Route;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_view);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        mListView = (ListView) findViewById(R.id.listView);

        Intent intent = getIntent();
        int language = intent.getIntExtra("language", 0);
        displayInform(language);

    }


    public void displayInform(int language) {
        BufferedReader reader = null;
        InputStream inputStream = null;

        try {
            url = new URL(jsonurl);
            connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }


            if (buffer != null && buffer.length() != 0) {
                String content = buffer.toString();
                JSONArray jsonarray = new JSONArray(content);
                for (int i = 0; i < jsonarray.length(); i++) {
                    JSONObject data = jsonarray.getJSONObject(i);
                    if (language == 0) {
                        title = "地點 : " + data.getString("Title_tc").toString();
                        distinct = "地區 : " + data.getString("District_tc").toString();
                        Route = "路線 : " + data.getString("Route_tc").toString();
                        Access = "交通 : " + data.getString("HowToAccess_tc").toString();
                        MapURL = data.getString("MapURL_tc").toString();
                        Latitude = data.getString("Latitude").toString();
                        Longitude = data.getString("Longitude").toString();
                        Route = Route.replaceAll("<br>", "\n");
                        Access = Access.replaceAll("<br>", "\n");
                        Latitude = Latitude.replaceAll(",", "");
                        Longitude = Longitude.replaceAll(",", "");
                        Data datas = new Data(title, distinct, Route, Access, MapURL, Latitude, Longitude);
                        Datalist.add(datas);
                    }else if (language == 1){
                        title = "地点 : " + data.getString("Title_sc").toString();
                        distinct = "地区 : " + data.getString("District_sc").toString();
                        Route = "路线 : " + data.getString("Route_sc").toString();
                        Access = "交通 : " + data.getString("HowToAccess_sc").toString();
                        MapURL = data.getString("MapURL_sc").toString();
                        Latitude = data.getString("Latitude").toString();
                        Longitude = data.getString("Longitude").toString();
                        Route = Route.replaceAll("<br>", "\n");
                        Access = Access.replaceAll("<br>", "\n");
                        Latitude = Latitude.replaceAll(",", "");
                        Longitude = Longitude.replaceAll(",", "");
                        Data datas = new Data(title, distinct, Route, Access, MapURL, Latitude, Longitude);
                        Datalist.add(datas);
                    }else{
                        title = "Title : " + data.getString("Title_en").toString();
                        distinct = "District : " + data.getString("District_en").toString();
                        Route = "Route : " + data.getString("Route_en").toString();
                        Access = "Access : " + data.getString("HowToAccess_en").toString();
                        MapURL = data.getString("MapURL_en").toString();
                        Latitude = data.getString("Latitude").toString();
                        Longitude = data.getString("Longitude").toString();
                        Route = Route.replaceAll("<br>", "\n");
                        Access = Access.replaceAll("<br>", "\n");
                        Latitude =  Latitude.replaceAll(",", "");
                        Longitude =   Longitude.replaceAll(",", "");
                        Data datas = new Data(title, distinct, Route, Access, MapURL, Latitude, Longitude);
                        Datalist.add(datas);
                    }
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        DataListAdapter adapter = new DataListAdapter(this, R.layout.adapter_view_layout, Datalist);
        mListView.setAdapter(adapter);

        String title ;
        String searchgps; // defined the button value
        String mapphoto ;   // defined the map photo display value
        String exit ; // defined the exit value ;

        if (language == 0){
            title = "資料";
            searchgps = "搜索定位" ;
            mapphoto = "地圖圖片" ;
            exit = "離開" ;
        }else if (language == 1){
            title = "资料";
            searchgps = "搜索定位" ;
            mapphoto = "地图图片" ;
            exit = "离开" ;
        }else{
            title = "The information";
            searchgps = "Search GPS" ;
            mapphoto = "Map Photo" ;
            exit = "Exit" ;
        }


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String theLatitude = "";
                String theLongitude  = "";

                if (language == 0){
                    theLatitude = "緯度 : " + (String) Datalist.get(position).getTitle();
                    theLongitude = "緯度 : " + (String) Datalist.get(position).getTitle();
                }else if(language == 1){
                    theLatitude = "纬度 : " + (String) Datalist.get(position).getTitle();
                    theLongitude = "经度 : " + (String) Datalist.get(position).getTitle();
                }else{
                    theLatitude = "Latitude : " + (String) Datalist.get(position).getTitle();
                    theLongitude = "Longitude : " + (String) Datalist.get(position).getTitle();
                }
                String diagmessage = (CharSequence) Datalist.get(position).getTitle() + "\n" + (CharSequence) Datalist.get(position).getDistrict() + "\n" + (CharSequence) Datalist.get(position).getRoute() + "\n" + (CharSequence) Datalist.get(position).getAccessway() + "\n" + theLatitude + "\n" + theLongitude;
                AlertDialog.Builder builder = new AlertDialog.Builder(DisplayActivity.this);
                builder.setTitle(Html.fromHtml("<font color = '#E91E63'>" + title + "</font>"));
                builder.setMessage(diagmessage);
                builder.setCancelable(false);

                builder.setNeutralButton(searchgps, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String googlegpsurl = "https://www.google.com/maps/search/?api=1&query=" + Datalist.get(position).getLatitude() + "%2C" + Datalist.get(position).getLongitude();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(googlegpsurl)));

                    }
                });

                builder.setNegativeButton(mapphoto, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Datalist.get(position).getMapurl())));

                    }
                });


                builder.setPositiveButton(exit, new DialogInterface.OnClickListener() {
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