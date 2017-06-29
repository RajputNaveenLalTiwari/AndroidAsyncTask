package com.example.asynctask.asynctask;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.asynctask.parsers.MyXmlPullParser;
import com.example.asynctask.interfaces.Result;
import com.example.asynctask.models.ApplicationDetailsModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 2114 on 22-06-2017.
 */

public class MyAsyncTask extends AsyncTask<String,Void,HashMap<String,List<ApplicationDetailsModel>>>
{

    private Result result;
    public MyAsyncTask(Activity activity)
    {
        result = (Result) activity;
    }

    @Override
    protected void onPreExecute()
    {

    }

    @Override
    protected HashMap<String, List<ApplicationDetailsModel>> doInBackground(String... params)
    {
        return new MyXmlPullParser().xmlPullParser(params[0],params[1]);
    }

    @Override
    protected void onPostExecute(HashMap<String, List<ApplicationDetailsModel>> stringListHashMap)
    {
        result.getResult(stringListHashMap);
    }
}
