package com.rhandycana.AhorcadoFinal1.repository;

import com.rhandycana.AhorcadoFinal1.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByCorreoUsuario(String correoUsuario);
    boolean existsByNombreUsuarioAndApellidoUsuario(String nombreUsuario, String apellidoUsuario);
}