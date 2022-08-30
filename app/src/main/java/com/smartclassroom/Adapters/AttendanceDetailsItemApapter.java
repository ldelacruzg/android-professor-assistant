package com.smartclassroom.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.smartclassroom.Models.AttendanceDetail;
import com.smartclassroom.R;

import java.util.List;

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponents();
        }

        public void initComponents() {
            switchAttendance = itemView.findViewById(R.id.switchAttendance);
        }

        public void bind(AttendanceDetail attendanceDetail) {
            switchAttendance.setText(attendanceDetail.getFullName());
            switchAttendance.setChecked(attendanceDetail.isAttendance());
        }
    }
}
