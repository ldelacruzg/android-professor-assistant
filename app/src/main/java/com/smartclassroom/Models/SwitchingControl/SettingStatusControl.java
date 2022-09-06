package com.smartclassroom.Models.SwitchingControl;

import android.widget.ImageView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class SettingStatusControl {
    private int status;
    private final ImageView imageView;
    private final int imageOn;
    private final int imageOff;
    private final int imageDefault;
}
