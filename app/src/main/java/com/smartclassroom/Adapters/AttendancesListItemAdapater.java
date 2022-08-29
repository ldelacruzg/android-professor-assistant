package com.smartclassroom.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartclassroom.Models.Attendance;

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return attendances.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponents();
        }

        private void initComponents() {
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }

        public void bind(Attendance attendance) {
            //text1.setText(at);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Attendance attendance, int position);
    }
}
