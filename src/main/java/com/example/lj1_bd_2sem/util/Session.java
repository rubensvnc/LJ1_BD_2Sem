package com.example.lj1_bd_2sem.util;

import com.example.lj1_bd_2sem.model.Usuario;

public class Session {
    private static Usuario currentUser = null;

    public static Usuario getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Usuario user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}