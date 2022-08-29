package com.smartclassroom.Services;

import com.smartclassroom.Models.Subject;
import com.smartclassroom.Models.Teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
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
}
