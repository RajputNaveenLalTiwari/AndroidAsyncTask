package com.example.asynctask.parsers;

import android.util.Log;

import com.example.asynctask.models.ApplicationDetailsModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 2114 on 22-06-2017.
 */

public class MyXmlPullParser
{
    private static final String TAG = "MyXmlPullParser";

    public HashMap<String,List<ApplicationDetailsModel>> xmlPullParser(String tag, String url)
    {
        URL download_url = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        HashMap<String,List<ApplicationDetailsModel>> final_result = null;

        try
        {
            download_url = new URL(url);

            httpURLConnection = (HttpURLConnection) download_url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            inputStream = httpURLConnection.getInputStream();

            final_result = parseData(inputStream,tag);

            return final_result;
        }
        catch (MalformedURLException e)
        {
            Log.e(TAG,"Error while creating URL"+e.getMessage());
        }
        catch (IOException e)
        {
            Log.e(TAG,"Error while opening connection"+e.getMessage());
        }

        return null;
    }

    private HashMap<String,List<ApplicationDetailsModel>> parseData(InputStream inputStream,String key)
    {
        if (inputStream!=null)
        {
            String tag_value = null;
            XmlPullParserFactory xmlPullParserFactory = null;
            XmlPullParser xmlPullParser = null;
            List<ApplicationDetailsModel> resultant_data = null;
            ApplicationDetailsModel detailsModel = null;
            HashMap<String,List<ApplicationDetailsModel>> final_result;
            try
            {
                xmlPullParserFactory = XmlPullParserFactory.newInstance();
                xmlPullParserFactory.setNamespaceAware(true);

                if (xmlPullParserFactory!=null)
                {
                    xmlPullParser = xmlPullParserFactory.newPullParser();

                    if (xmlPullParser!=null)
                    {
                        xmlPullParser.setInput(inputStream,null);

                        int event_type = xmlPullParser.getEventType();

                        resultant_data = new ArrayList<>();

                        while( event_type != XmlPullParser.END_DOCUMENT )
                        {
                            String tagName = xmlPullParser.getName();

                            switch ( event_type )
                            {
                                case XmlPullParser.START_TAG:
                                    if( tagName.equalsIgnoreCase("app") )
                                    {
                                        detailsModel = new ApplicationDetailsModel();
                                    }

                                    break;

                                case XmlPullParser.TEXT:
                                    tag_value = xmlPullParser.getText();
                                    break;

                                case XmlPullParser.END_TAG:

                                    if( tagName.equalsIgnoreCase("app") )
                                    {
                                        if (detailsModel!=null)
                                        {
                                            resultant_data.add(detailsModel);
                                        }

                                    }
                                    else if( tagName.equalsIgnoreCase("name") )
                                    {
                                        detailsModel.appName = tag_value;
                                    }
                                    else if( tagName.equalsIgnoreCase("url") )
                                    {
                                        detailsModel.url = tag_value;
                                    }
                                    else if( tagName.equalsIgnoreCase("image") )
                                    {
                                        detailsModel.bitmapUrl = tag_value;
                                    }
                                    break;

                                default:
                                    break;
                            }

                            event_type = xmlPullParser.next();
                        }

                    }
                }
                final_result = new HashMap<>();
                final_result.put(key,resultant_data);
                return final_result;
            }
            catch (XmlPullParserException e)
            {
                Log.e(TAG,"Error while creating xmlpullparser "+e.getMessage());
            }
            catch (IOException e)
            {
                Log.e(TAG,"Error while finding next event type"+e.getMessage());
            }
        }
        return null;
    }
}
