package com.smartclassroom.Adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.smartclassroom.Models.Device;
import com.smartclassroom.R;
import com.smartclassroom.Utils.RetrofitManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeviceListItemAdapter extends RecyclerView.Adapter<DeviceListItemAdapter.ViewHolder> {
    private int layout;
    private List<Device> devices;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public DeviceListItemAdapter(int layout, List<Device> devices) {
        this.layout = layout;
        this.devices = devices;
        Collections.sort(this.devices, (t1, t2) -> t1.getId() - t2.getId());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new DeviceListItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(devices.get(position));
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private SwitchMaterial switchDevice;
        private LinearProgressIndicator progressIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            initComponents();
        }

        private void initComponents() {
            switchDevice = itemView.findViewById(R.id.switchDevice);
            progressIndicator = itemView.findViewById(R.id.progressIndicator);
        }

        public void bind(Device device) {
            String tag = null;

            switch (device.getName()) {
                case "Light":
                    tag = "Turn on the light";
                    break;
                case "Air Conditioning":
                    tag = "Turn on the air conditioning";
                    break;
                case "Projector":
                    tag = "Turn on the projector";
                    break;
                default:
                    tag = device.getName();
            }

            switchDevice.setText(tag);
            switchDevice.setChecked(device.isEnable());

            switchDevice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                    Call<Void> changeDeviceStatus = RetrofitManager
                            .getSmartClassroomService()
                            .changeDeviceStatus(device.getId(), !device.isEnable());

                    changeDeviceStatus.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 200) {
                                Toast.makeText(view.getContext(), "Changed status...", Toast.LENGTH_SHORT).show();
                            } else {
                                switchDevice.setChecked(device.isEnable());
                            }
                            progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(view.getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                            switchDevice.setChecked(device.isEnable());
                            progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                        }
                    });
                }
            });
        }
    }
}
