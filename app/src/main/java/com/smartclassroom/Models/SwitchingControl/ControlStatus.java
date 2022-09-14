package com.smartclassroom.Models.SwitchingControl;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class ControlStatus {
    @SerializedName("aire")
    private int air;

    @SerializedName("proyector")
    private int projector;

    @SerializedName("luces")
    private int light;

    @SerializedName("puerta")
    private int door;

    @SerializedName("estado")
    private int status;
}
