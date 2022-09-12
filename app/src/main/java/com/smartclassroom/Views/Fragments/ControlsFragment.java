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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ControlsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControlsFragment extends Fragment {
    View view;
    ImageView imageViewLightControl, imageViewDoorControl, imageViewAirControl, imageViewProjectorControl;
    LinearProgressIndicator progressIndicator;
    SettingStatusControl lightSetting, doorSetting, airSetting, projectorSetting;
    Button buttonRefreshControls;
    TextView textViewIp;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ControlsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ControlsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ControlsFragment newInstance(String param1, String param2) {
        ControlsFragment fragment = new ControlsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                    loadControlStatus();
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
                            changeControlStatus(lightSetting, false);
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
            @Override
            public void onClick(View view) {
                progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                Call<SwitchingControl> switchingDoor = RetrofitManager
                        .getSmartClassroomControl()
                        .switchingDoor();

                switchingDoor.enqueue(new Callback<SwitchingControl>() {
                    @Override
                    public void onResponse(Call<SwitchingControl> call, Response<SwitchingControl> response) {
                        if (response.code() == 200) {
                            SwitchingControl switchingControl = response.body();
                            doorSetting.setStatus(switchingControl.getStatus());
                            changeControlStatus(doorSetting, false);
                        } else {
                            Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        }
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<SwitchingControl> call, Throwable t) {
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
                Call<SwitchingControl> switchingAir = RetrofitManager
                        .getSmartClassroomControl()
                        .switchingAir();

                switchingAir.enqueue(new Callback<SwitchingControl>() {
                    @Override
                    public void onResponse(Call<SwitchingControl> call, Response<SwitchingControl> response) {
                        if (response.code() == 200) {
                            SwitchingControl switchingControl = response.body();
                            airSetting.setStatus(switchingControl.getStatus());
                            changeControlStatus(airSetting, true);
                        } else {
                            Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        }
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<SwitchingControl> call, Throwable t) {
                        Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                    }
                });
            }
        });

        // projector control
        imageViewProjectorControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                Call<SwitchingControl> switchingProjector = RetrofitManager
                        .getSmartClassroomControl()
                        .switchingProjector();

                switchingProjector.enqueue(new Callback<SwitchingControl>() {
                    @Override
                    public void onResponse(Call<SwitchingControl> call, Response<SwitchingControl> response) {
                        if (response.code() == 200) {
                            SwitchingControl switchingControl = response.body();
                            projectorSetting.setStatus(switchingControl.getStatus());
                            changeControlStatus(projectorSetting, true);
                        } else {
                            Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        }
                        progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
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
                    doorSetting.setStatus(controlStatus1.getDoor());
                    changeControlStatus(doorSetting, false);

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