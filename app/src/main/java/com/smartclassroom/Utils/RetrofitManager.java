package com.smartclassroom.Utils;

import com.smartclassroom.Services.SmartClassroomControl;
import com.smartclassroom.Services.SmartClassroomService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    public static SmartClassroomService getSmartClassroomService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.SMART_CLASSROOM_API_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(SmartClassroomService.class);
    }

    public static SmartClassroomControl getSmartClassroomControl() {
        System.out.println("Devices ip =====>" + Global.SMART_CLASSROOM_CONTROL_URL_BASE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Global.SMART_CLASSROOM_CONTROL_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(SmartClassroomControl.class);
    }
}
