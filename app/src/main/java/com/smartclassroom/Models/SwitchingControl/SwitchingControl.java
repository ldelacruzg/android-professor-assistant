package com.smartclassroom.Models.SwitchingControl;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class SwitchingControl {
    @SerializedName("estado")
    private int status;
}
