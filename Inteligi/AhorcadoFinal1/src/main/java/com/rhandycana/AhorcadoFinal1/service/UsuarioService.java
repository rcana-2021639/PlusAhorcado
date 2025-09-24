package com.rhandycana.AhorcadoFinal1.service;

import com.rhandycana.AhorcadoFinal1.model.Usuario;
import java.util.List;

public interface UsuarioService {
    List<Usuario> getAllUsuarios();
    Usuario getUsuarioById(Integer codigoUsuario);
    Usuario createUsuario(Usuario usuario);
    Usuario updateUsuario(Integer codigoUsuario, Usuario usuario);
    void deleteUsuario(Integer codigoUsuario);
}