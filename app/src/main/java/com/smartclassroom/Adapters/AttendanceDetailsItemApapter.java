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
import com.smartclassroom.Models.AttendanceDetail;
import com.smartclassroom.Models.Schedule;
import com.smartclassroom.R;
import com.smartclassroom.Utils.Global;
import com.smartclassroom.Utils.RetrofitManager;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void bind(AttendanceDetail attendanceDetail) {
            switchAttendance.setText(attendanceDetail.getFullName());
            switchAttendance.setChecked(attendanceDetail.isAttendance());

            System.out.println("SUBJECTTTTT" + Global.SELECTED_SUBJECT);

            // verificar si se puede modificar la asistencia
            System.out.println("NUMEROOOOOOO"+Global.SELECTED_SUBJECT.getSchedules().size());
            if (Global.SELECTED_SUBJECT.getSchedules().size() > 0) {
                DayOfWeek dayOfWeek = LocalDateTime.now().getDayOfWeek();
                System.out.println("Dia en letras " + dayOfWeek);
                Schedule schedule = null;

                for (Schedule el : Global.SELECTED_SUBJECT.getSchedules()) {
                    if (el.getLdtDate().getDayOfWeek().equals(dayOfWeek)) {
                        schedule = el;
                        break;
                    }
                }

                if (schedule == null || !schedule.isEditable()) {
                    switchAttendance.setEnabled(false);
                }
            }

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
