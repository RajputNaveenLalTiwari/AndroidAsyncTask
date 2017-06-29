package com.example.asynctask.interfaces;

import com.example.asynctask.models.ApplicationDetailsModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by 2114 on 22-06-2017.
 */

public interface Result
{
    void getResult(HashMap<String,List<ApplicationDetailsModel>> hashMap);
}
