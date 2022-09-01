package com.smartclassroom.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartclassroom.Models.Student;
import com.smartclassroom.R;

import java.util.List;

public class StudentListItemAdapter extends RecyclerView.Adapter<StudentListItemAdapter.ViewHolder> {
    private List<Student> students;
    private int layout;
    private OnItemClickListener listener;

    public StudentListItemAdapter(List<Student> students, int layout, OnItemClickListener listener) {
        this.students = students;
        this.layout = layout;
        this.listener = listener;
    }

    public StudentListItemAdapter(List<Student> students, int layout) {
        this.students = students;
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
        // se colocan los datos
        holder.bind(students.get(position));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text1, text2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initComponents();

        }

        private void initComponents() {
            text1 = itemView.findViewById(R.id.textViewFullName);
            text2 = itemView.findViewById(R.id.textViewEmail);
        }

        public void bind(Student student) {
            text1.setText(student.getLastname() + " " + student.getName());
            text2.setText(student.getEmail());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Student student, int position);
    }
}
