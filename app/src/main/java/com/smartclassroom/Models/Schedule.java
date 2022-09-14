package com.smartclassroom.Models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;
import com.smartclassroom.Utils.Global;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
public class Schedule {
    private int id;

    @SerializedName("dia_horario")
    private String ldtDate;

    @SerializedName("hora_inicio")
    private String ldtTimeStart;

    @SerializedName("hora_fin")
    private String ldtTimeEnd;

    public Schedule() {
    }

    public int getId() {
        return id;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime getLdtDate() {
        return LocalDateTime.parse(ldtDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime getLdtTimeStart() {
        return LocalDateTime.parse(ldtTimeStart);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDateTime getLdtTimeEnd() {
        return LocalDateTime.parse(ldtTimeEnd);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.parse(
                LocalDateTime.now(ZoneId.of("America/Bogota")).toString().substring(0, 19)
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean isEditable() {
        LocalDateTime currentDateTime = getCurrentDateTime();
        LocalDateTime ldtTimeStart = buildDateTime(currentDateTime, this.getLdtTimeStart());
        LocalDateTime ldtTimeEnd = buildDateTime(currentDateTime, this.getLdtTimeEnd());

        /*return currentDateTime.getDayOfWeek().equals(getLdtDate().getDayOfWeek())
                && currentDateTime.isAfter(ldtTimeStart)
                && currentDateTime.isBefore(ldtTimeEnd);*/

        return Global.SELECTED_ATTENDANCE.getOnlyDate().equals(currentDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE))
            &&currentDateTime.isAfter(ldtTimeStart) && currentDateTime.isBefore(ldtTimeEnd);
    }

    private String getFullTime(LocalDateTime dateTime) {
        String dateTimeAux = dateTime.toString();
        return dateTimeAux.substring(dateTimeAux.indexOf("T") + 1);
    }

    private String getFullDate(LocalDateTime dateTime) {
        String dateTimeAux = dateTime.toString();
        return dateTimeAux.substring(0, dateTimeAux.indexOf("T"));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private LocalDateTime buildDateTime(LocalDateTime date, LocalDateTime time) {
        return LocalDateTime.parse(
                getFullDate(date) + "T" + getFullTime(time)
        );
    }
}
