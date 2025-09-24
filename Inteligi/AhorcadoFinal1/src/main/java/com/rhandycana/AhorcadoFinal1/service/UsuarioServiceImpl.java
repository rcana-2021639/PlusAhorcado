package com.rhandycana.AhorcadoFinal1.service;

import com.rhandycana.AhorcadoFinal1.exceptiones.DuplicateResourceException;
import com.rhandycana.AhorcadoFinal1.exceptiones.ResourceNotFoundException;
import com.rhandycana.AhorcadoFinal1.model.Usuario;
import com.rhandycana.AhorcadoFinal1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Date;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuarioById(Integer codigoUsuario) {
        return usuarioRepository.findById(codigoUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con código: " + codigoUsuario));
    }

    @Override
    public Usuario createUsuario(Usuario usuario) {
        if (!usuario.getCorreoUsuario().endsWith("@gmail.com")) {
            throw new DuplicateResourceException("El correo debe terminar en @gmail.com");
        }
        if (usuarioRepository.existsByCorreoUsuario(usuario.getCorreoUsuario())) {
            throw new DuplicateResourceException("Ya existe un usuario con el correo: " + usuario.getCorreoUsuario());
        }
        if (usuarioRepository.existsByNombreUsuarioAndApellidoUsuario(usuario.getNombreUsuario(), usuario.getApellidoUsuario())) {
            throw new DuplicateResourceException("Ya existe un usuario con el mismo nombre y apellido.");
        }
        usuario.setFechaRegistro(new Date());
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Integer codigoUsuario, Usuario usuarioDetails) {
        Usuario usuario = getUsuarioById(codigoUsuario);

        if (!usuarioDetails.getCorreoUsuario().endsWith("@gmail.com")) {
            throw new DuplicateResourceException("El correo debe terminar en @gmail.com");
        }
        if (!usuario.getCorreoUsuario().equals(usuarioDetails.getCorreoUsuario()) &&
                usuarioRepository.existsByCorreoUsuario(usuarioDetails.getCorreoUsuario())) {
            throw new DuplicateResourceException("Ya existe un usuario con el correo: " + usuarioDetails.getCorreoUsuario());
        }
        if ((!usuario.getNombreUsuario().equals(usuarioDetails.getNombreUsuario()) ||
                !usuario.getApellidoUsuario().equals(usuarioDetails.getApellidoUsuario())) &&
                usuarioRepository.existsByNombreUsuarioAndApellidoUsuario(usuarioDetails.getNombreUsuario(), usuarioDetails.getApellidoUsuario())) {
            throw new DuplicateResourceException("Ya existe un usuario con el mismo nombre y apellido.");
        }

        usuario.setNombreUsuario(usuarioDetails.getNombreUsuario());
        usuario.setApellidoUsuario(usuarioDetails.getApellidoUsuario());
        usuario.setCorreoUsuario(usuarioDetails.getCorreoUsuario());
        usuario.setContraseñaUsuario(usuarioDetails.getContraseñaUsuario());

        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuario(Integer codigoUsuario) {
        Usuario usuario = getUsuarioById(codigoUsuario);
        usuarioRepository.delete(usuario);
    }
}