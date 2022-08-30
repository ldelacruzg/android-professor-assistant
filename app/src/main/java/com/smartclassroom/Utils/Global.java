package com.smartclassroom.Utils;

import com.google.gson.Gson;
import com.smartclassroom.Models.Subject;
import com.smartclassroom.Models.Teacher;

public class Global {
    public static final String SMART_CLASSROOM_API_URL_BASE = "https://blooming-tundra-94814.herokuapp.com/api/";
    public static final Gson GSON_INSTANCE = new Gson();
    public static Teacher LOGGED_TEACHER = null;
    public static Subject SELECTED_SUBJECT = null;
}
