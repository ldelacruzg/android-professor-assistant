package com.smartclassroom.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartclassroom.Models.Attendance;

import java.util.Date;
import java.util.List;

public class AttendancesListItemAdapater extends RecyclerView.Adapter<AttendancesListItemAdapater.ViewHolder> {
    private int layout;
    private List<Attendance> attendances;
    private OnItemClickListener listener;

    public AttendancesListItemAdapater(int layout, List<Attendance> attendances, OnItemClickListener listener) {
        this.layout = layout;
        this.attendances = attendances;
        this.listener = listener;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(attendances.get(position));
    }

    @Override
    public int getItemCount() {
        return attendances.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponents();
        }

        private void initComponents() {
            text1 = itemView.findViewById(android.R.id.text1);
        }

        public void bind(Attendance attendance) {
            text1.setText("Date: " + attendance.getOnlyDate() + " Time: " + attendance.getOnlyTime());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(attendance, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Attendance attendance, int position);
    }
}
