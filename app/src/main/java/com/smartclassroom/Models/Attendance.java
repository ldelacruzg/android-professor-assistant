package com.smartclassroom.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Attendance {
    @SerializedName("fechaAsistencia")
    private String date;

    public String getOnlyDate() {
        return this.date.substring(0, 10);
    }

    public String getOnlyTime() {
        return this.date.substring(11, 19);
    }
}
