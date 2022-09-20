package com.smartclassroom.Views.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.smartclassroom.Models.SwitchingControl.ControlStatus;
import com.smartclassroom.Models.SwitchingControl.LightControl;
import com.smartclassroom.Models.SwitchingControl.SettingStatusControl;
import com.smartclassroom.Models.SwitchingControl.SwitchingControl;
import com.smartclassroom.R;
import com.smartclassroom.Utils.Global;
import com.smartclassroom.Utils.RetrofitManager;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControlsFragment extends Fragment {
    View view;
    ImageView imageViewLightControl, imageViewDoorControl, imageViewAirControl, imageViewProjectorControl,
        imageViewAirControlLower, imageViewAirControlUp;
    LinearProgressIndicator progressIndicator;
    SettingStatusControl lightSetting, doorSetting, airSetting, projectorSetting;
    Button buttonRefreshControls;
    TextView textViewIp;

    public ControlsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_controls, container, false);

        // init components
        initComponents();

        setTextViewIp();

        // controls config
        lightSetting = new SettingStatusControl(
                imageViewLightControl, R.drawable.ic_light_on, R.drawable.ic_light_off, R.drawable.ic_light
        );

        doorSetting = new SettingStatusControl(
                imageViewDoorControl, R.drawable.ic_door_on, R.drawable.ic_door_off, R.drawable.ic_door
        );

        airSetting = new SettingStatusControl(
                imageViewAirControl, R.drawable.ic_air_on, R.drawable.ic_air_off, R.drawable.ic_air
        );

        projectorSetting = new SettingStatusControl(
                imageViewProjectorControl, R.drawable.ic_projector_on, R.drawable.ic_projector_off, R.drawable.ic_projector
        );

        // load controls status
        buttonRefreshControls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Global.SMART_CLASSROOM_CONTROL_URL_BASE.contains("http")) {
                    //loadControlStatus();
                    requestLoadLightStatus();
                } else {
                    Toast.makeText(getContext(), "No server has been established", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // controls events
        // light control
        imageViewLightControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                Call<LightControl> lightControlCall = RetrofitManager
                        .getSmartClassroomControl()
                        .switchingLights();

                lightControlCall.enqueue(new Callback<LightControl>() {
                    @Override
                    public void onResponse(Call<LightControl> call, Response<LightControl> response) {
                        if (response.code() == 200) {
                            LightControl lightControl = response.body();
                            lightSetting.setStatus(lightControl.getStatus());
                            changeControlStatus(lightSetting, true);
                        } else {
                            Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        }
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<LightControl> call, Throwable t) {
                        Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                    }
                });
            }
        });

        // door control
        imageViewDoorControl.setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View view) {
                imageViewDoorControl.setImageResource(R.drawable.ic_door_off);
                progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                Call<ControlStatus> switchingDoor = RetrofitManager
                        .getSmartClassroomControl()
                        .openDoor();

                switchingDoor.enqueue(new Callback<ControlStatus>() {
                    @Override
                    public void onResponse(Call<ControlStatus> call, Response<ControlStatus> response) {
                        if (response.code() == 200) {
                            ControlStatus switchingControl = response.body();
                            if (switchingControl.getStatus() == 1) {
                                Toast.makeText(getContext(), "Door open", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        }
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                        imageViewDoorControl.setImageResource(R.drawable.ic_door_on);
                    }

                    @Override
                    public void onFailure(Call<ControlStatus> call, Throwable t) {
                        Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                    }
                });
            }
        });

        // air control
        imageViewAirControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                imageViewAirControl.setImageResource(R.drawable.ic_power_off);
                Call<SwitchingControl> switchingAir = RetrofitManager
                        .getSmartClassroomControl()
                        .switchingAir();

                switchingAir.enqueue(new Callback<SwitchingControl>() {
                    @Override
                    public void onResponse(Call<SwitchingControl> call, Response<SwitchingControl> response) {
                        if (response.code() == 200) {
                            SwitchingControl switchingControl = response.body();
                            if (switchingControl.getStatus() == 1) {
                                Toast.makeText(getContext(), "Air On", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Air Off", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        }
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                        imageViewAirControl.setImageResource(R.drawable.ic_power_on);
                    }

                    @Override
                    public void onFailure(Call<SwitchingControl> call, Throwable t) {
                        Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                    }
                });
            }
        });

        /*
        imageViewAirControlLower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Bajar aire", Toast.LENGTH_SHORT).show();
            }
        });

        imageViewAirControlUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Subir aire", Toast.LENGTH_SHORT).show();
            }
        });
        */

        // projector control
        imageViewProjectorControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                imageViewProjectorControl.setImageResource(R.drawable.ic_projector_off);
                Call<SwitchingControl> switchingProjector = RetrofitManager
                        .getSmartClassroomControl()
                        .switchingProjector();

                switchingProjector.enqueue(new Callback<SwitchingControl>() {
                    @Override
                    public void onResponse(Call<SwitchingControl> call, Response<SwitchingControl> response) {
                        if (response.code() == 200) {
                            SwitchingControl switchingControl = response.body();
                            if (switchingControl.getStatus() == 1) {
                                Toast.makeText(getContext(), "Projector On", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Projector Off", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        }
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                        imageViewProjectorControl.setImageResource(R.drawable.ic_projector_on);
                    }

                    @Override
                    public void onFailure(Call<SwitchingControl> call, Throwable t) {
                        Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                    }
                });
            }
        });

        return view;
    }

    private void initComponents() {
        imageViewLightControl = view.findViewById(R.id.imageViewLightControl);
        imageViewDoorControl = view.findViewById(R.id.imageViewDoorControl);
        imageViewAirControl = view.findViewById(R.id.imageViewAirControl);
        imageViewProjectorControl = view.findViewById(R.id.imageViewProjectorControl);
        progressIndicator = view.findViewById(R.id.progressIndicator);
        buttonRefreshControls = view.findViewById(R.id.buttonRefreshControls);
        textViewIp = view.findViewById(R.id.textViewIp);
    }

    private void setTextViewIp() {
        textViewIp.setText(Global.SMART_CLASSROOM_CONTROL_URL_BASE);
    }

    private void requestLoadLightStatus() {
        progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
        setTextViewIp();

        Call<ControlStatus> lightStatus = RetrofitManager
                .getSmartClassroomControl()
                .getLightStatus();

        lightStatus.enqueue(new Callback<ControlStatus>() {
            @Override
            public void onResponse(Call<ControlStatus> call, Response<ControlStatus> response) {
                if (response.code() == 200) {
                    ControlStatus controlStatus1 = response.body();

                    lightSetting.setStatus(controlStatus1.getStatus());
                    changeControlStatus(lightSetting, false);
                } else {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }
                progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ControlStatus> call, Throwable t) {
                String message = t.getMessage();
                progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
            }
        });
    }

    private void loadControlStatus() {
        progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
        setTextViewIp();

        Call<ControlStatus> controlStatus = RetrofitManager
                .getSmartClassroomControl()
                .getControlStatus();

        controlStatus.enqueue(new Callback<ControlStatus>() {
            @Override
            public void onResponse(Call<ControlStatus> call, Response<ControlStatus> response) {
                if (response.code() == 200) {
                    ControlStatus controlStatus1 = response.body();

                    // init light status
                    lightSetting.setStatus(controlStatus1.getLight());
                    changeControlStatus(lightSetting, false);

                    // init door status
                    //doorSetting.setStatus(controlStatus1.getDoor());
                    //changeControlStatus(doorSetting, false);

                    // init air conditioning status 0=off
                    airSetting.setStatus(controlStatus1.getAir());
                    changeControlStatus(airSetting, true);

                    // init projector status 0=off
                    projectorSetting.setStatus(controlStatus1.getProjector());
                    changeControlStatus(projectorSetting, true);
                } else {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                }

                progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ControlStatus> call, Throwable t) {
                Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
            }
        });
    }

    private void changeControlStatus(SettingStatusControl settingStatusControl, boolean reverse) {
        if (settingStatusControl.getStatus() != 1 && settingStatusControl.getStatus() != 0) {
            settingStatusControl.getImageView().setImageResource(settingStatusControl.getImageDefault());
            return;
        }

        if (reverse && settingStatusControl.getStatus() == 0) {
            settingStatusControl.getImageView().setImageResource(settingStatusControl.getImageOff());
        } else if (!reverse && settingStatusControl.getStatus() == 0) {
            settingStatusControl.getImageView().setImageResource(settingStatusControl.getImageOn());
        }

        if (reverse && settingStatusControl.getStatus() == 1) {
            settingStatusControl.getImageView().setImageResource(settingStatusControl.getImageOn());
        } else if (!reverse && settingStatusControl.getStatus() == 1) {
            settingStatusControl.getImageView().setImageResource(settingStatusControl.getImageOff());
        }
    }
}