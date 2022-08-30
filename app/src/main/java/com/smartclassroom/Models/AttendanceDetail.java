package com.smartclassroom.Models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class AttendanceDetail {
    @SerializedName("asistenciaId")
    private int id;

    @SerializedName("nombres")
    private String fullName;

    @SerializedName("valido")
    private boolean attendance;
}
