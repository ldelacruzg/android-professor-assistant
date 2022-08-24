package com.smartclassroom.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter @Setter
@ToString
public class Subject {
    private final String name;
    private final Teacher teacher;
    private List<Student> students;
}
