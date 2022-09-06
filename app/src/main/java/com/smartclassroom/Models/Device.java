package com.smartclassroom.Models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Device {
    private int id;

    @SerializedName("nombre")
    private String name;

    private boolean enable;
}
