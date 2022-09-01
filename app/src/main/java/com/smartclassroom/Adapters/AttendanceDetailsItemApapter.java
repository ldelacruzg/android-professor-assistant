package com.smartclassroom.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.smartclassroom.Models.AttendanceDetail;
import com.smartclassroom.R;
import com.smartclassroom.Utils.RetrofitManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceDetailsItemApapter extends RecyclerView.Adapter<AttendanceDetailsItemApapter.ViewHolder> {
    private List<AttendanceDetail> attendanceDetailList;
    private int layout;

    public AttendanceDetailsItemApapter(List<AttendanceDetail> attendanceDetailList, int layout) {
        this.attendanceDetailList = attendanceDetailList;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(attendanceDetailList.get(position));
    }

    @Override
    public int getItemCount() {
        return attendanceDetailList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        SwitchMaterial switchAttendance;
        LinearProgressIndicator progressIndicator;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponents();
        }

        public void initComponents() {
            switchAttendance = itemView.findViewById(R.id.switchAttendance);
            progressIndicator = itemView.findViewById(R.id.progressIndicator);
        }

        public void bind(AttendanceDetail attendanceDetail) {
            switchAttendance.setText(attendanceDetail.getFullName());
            switchAttendance.setChecked(attendanceDetail.isAttendance());

            switchAttendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressIndicator.setVisibility(LinearProgressIndicator.VISIBLE);
                    Call<Void> voidCall = RetrofitManager
                            .getSmartClassroomService()
                            .changeAttendance(attendanceDetail.getId(), !attendanceDetail.isAttendance());

                    voidCall.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.code() == 200)
                                Toast.makeText(view.getContext(), "Changed attendance...", Toast.LENGTH_SHORT).show();
                            else {
                                Toast.makeText(view.getContext(), "Unable to change attendance", Toast.LENGTH_SHORT).show();
                                switchAttendance.setChecked(attendanceDetail.isAttendance());
                            }
                            progressIndicator.setVisibility(LinearProgressIndicator.INVISIBLE);
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                        }
                    });
                }
            });
        }
    }
}
