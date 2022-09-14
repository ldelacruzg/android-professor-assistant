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

    @GET("/puerta-accion/")
    Call<SwitchingControl> switchingDoor();



    @GET("/registrar/{userEmail}")
    Call<Void> registerFingerprint(@Path("userEmail") String userEmail);

    @GET("/eliminar-huella/{userId}")
    Call<Void> deleteFingerprint(@Path("userId") int userId);

    /* NUEVOOO */
    @POST("/puerta")
    Call<ControlStatus> openDoor();

    @GET("/luces-estado/")
    Call<ControlStatus> getLightStatus();

    @GET("/luces/")
    Call<LightControl> switchingLights();

    @GET("/proyector-encender/")
    Call<SwitchingControl> switchingProjector();

    @GET("/aire-encender/")
    Call<SwitchingControl> switchingAir();
}
