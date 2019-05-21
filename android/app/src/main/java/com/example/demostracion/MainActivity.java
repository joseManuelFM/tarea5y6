package com.example.demostracion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    GridLayoutManager lay;
    RecyclerView recycler;
    menuAdapter men;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //seteamos los adaptadores ya creados menuadapter e item url

        recycler=findViewById(R.id.recyclerId);
        lay=new GridLayoutManager(this,2);
        recycler.setLayoutManager(lay);
        men=new menuAdapter(this);
        //validarPermisos(this,this);
        cargar();
        recycler.setAdapter(men);

    }

    private void cargar() {
        AsyncHttpClient client=new AsyncHttpClient();
        client.get("http://192.168.43.132:8000/image",null,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject dat=response.getJSONObject(i);
                        String url=dat.getString("url");
                        men.add(new item(url));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
            }
        });

    }
}
