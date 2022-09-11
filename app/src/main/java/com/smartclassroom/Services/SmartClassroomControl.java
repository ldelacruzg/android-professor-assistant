package com.smartclassroom.Services;

import com.smartclassroom.Models.SwitchingControl.ControlStatus;
import com.smartclassroom.Models.SwitchingControl.LightControl;
import com.smartclassroom.Models.SwitchingControl.SwitchingControl;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SmartClassroomControl {
    @GET("/dispositivos/estados/")
    Call<ControlStatus> getControlStatus();

    @GET("/luces/")
    Call<LightControl> switchingLights();

    @GET("/puerta-accion/")
    Call<SwitchingControl> switchingDoor();

    @GET("/aire/")
    Call<SwitchingControl> switchingAir();

    @GET("/proyector/")
    Call<SwitchingControl> switchingProjector();

    @POST("/fingerprint/{userEmail}")
    Call<Void> registerFingerprint(@Path("userEmail") String userEmail);

    @DELETE("/fingerprint/{userEmail}")
    Call<Void> deleteFingerprint(@Path("userEmail") String userEmail);
}
