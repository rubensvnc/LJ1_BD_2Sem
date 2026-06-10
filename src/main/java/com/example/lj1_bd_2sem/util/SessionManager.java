package com.example.lj1_bd_2sem.util;

import com.example.lj1_bd_2sem.model.Usuario;

public class SessionManager {
    private static Usuario usuarioLogado;

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }
}