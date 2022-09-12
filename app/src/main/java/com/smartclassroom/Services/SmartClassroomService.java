package com.smartclassroom.Services;

import com.smartclassroom.Models.Attendance;
import com.smartclassroom.Models.AttendanceDetail;
import com.smartclassroom.Models.Device;
import com.smartclassroom.Models.Subject;
import com.smartclassroom.Models.Teacher;
import com.smartclassroom.Models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SmartClassroomService {
    @GET("usuarios/email/{teacherEmail}")
    Call<Teacher> getTeacherByEmail(@Path("teacherEmail") String teacherEmail);

    @GET("materias/email/{teacherEmail}")
    Call<List<Subject>> getSubjectsByTeacher(@Path("teacherEmail") String teacherEmail);

    @GET("materias")
    Call<List<Subject>> getAllSubjects();

    @GET("materias/{id}")
    Call<Subject> getSubjectById(@Path("id") int id);

    @GET("materias/{idSubject}/fecha-asistencia")
    Call<List<Attendance>> getAllAttendancesBySubject(@Path("idSubject") int idSubject);

    @GET("materias/{idSubject}/asistencias/{dateAttendance}")
    Call<List<AttendanceDetail>> getStudentAttendances(@Path("idSubject") int idSubject, @Path("dateAttendance") String dateAttendance);

    @PUT("materias/validar/{idAttendance}/{state}")
    Call<Void> changeAttendance(@Path("idAttendance") int idAttendace, @Path("state") boolean state);

    @GET("dispositivos/usuario/{idUser}")
    Call<List<Device>> getConfigDeviceByUser(@Path("idUser") int idUser);

    @PUT("dispositivos/{idDevice}/{status}")
    Call<Void> changeDeviceStatus(@Path("idDevice") int idDevice, @Path("status") boolean status);

    @GET("usuarios/email/{userEmail}")
    Call<User> getUserByEmail(@Path("userEmail") String userEmail);
}
