package com.smartclassroom.Models;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class UserType {
    private final int id;

    @SerializedName("nombre")
    private final String name;

    @SerializedName("descripcion")
    private String description;
}
