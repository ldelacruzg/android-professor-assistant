package com.smartclassroom.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
public class Teacher extends User {
    @SerializedName("dispositivos")
    private List<Device> devices;

    public Teacher(String name, String lastname, String email) {
        super(name, lastname, email);
    }
}
