package com.smartclassroom.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter @Setter
@ToString
public class Subject {
    private int id;

    @SerializedName("nombre")
    private final String name;

    @SerializedName("creditos")
    private int credits;

    @SerializedName("usuarios")
    private List<Student> students;

    @SerializedName("horarios")
    private List<Schedule> schedules;
}
