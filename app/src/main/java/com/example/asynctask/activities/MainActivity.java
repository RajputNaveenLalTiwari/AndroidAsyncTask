package com.example.asynctask.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.asynctask.asynctask.MyAsyncTask;
import com.example.asynctask.R;
import com.example.asynctask.interfaces.Result;
import com.example.asynctask.models.ApplicationDetailsModel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Result
{
    private static final String TAG = "MainActivity";
    private Context context;
    private String[] app_basic_links,app_basic_tags;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        app_basic_links = getResources().getStringArray(R.array.appbasic_links);
        app_basic_tags = getResources().getStringArray(R.array.appbasic_tags);

        if (app_basic_links != null && app_basic_tags != null)
        {
            new MyAsyncTask(MainActivity.this).execute(app_basic_tags[1],app_basic_links[1]);
        }
        else
        {
            Toast.makeText(context,"Opp's links doesn't exists",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void getResult(HashMap<String, List<ApplicationDetailsModel>> hashMap)
    {
        Iterator iterator = hashMap.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry map = (Map.Entry)iterator.next();
            List<ApplicationDetailsModel> detailsModels = (List<ApplicationDetailsModel>) map.getValue();
            for (ApplicationDetailsModel detailsModel:detailsModels)
            {
                Log.i(TAG,detailsModel.appName);
                Log.i(TAG,detailsModel.url);
                Log.i(TAG,detailsModel.bitmapUrl);
            }
        }
    }
}
