package com.smartclassroom.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter @Setter
@ToString
public class User {
    @SerializedName("id_usuario")
    private int id;

    @SerializedName("nombre")
    private final String name;

    @SerializedName("apellido")
    private final String lastname;

    private String password;
    private final String email;

    @SerializedName("telefono")
    private String phone;

    @SerializedName("tipoUsuario")
    private UserType userType;

    private boolean userRegisterFingerprint;
}
