package com.proyecto_citas_medicas.login;

public class User {
    private String correo;
    private String contraseña;
    private  String userType;

    public User(String correo, String contraseña,  String userType) {

        this.correo = correo;
        this.contraseña = contraseña;
        this.userType = userType;
    }

      public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String  getUserType() {
        return userType;
    }
}
