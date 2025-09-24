package com.rhandycana.AhorcadoFinal1.controller;

import com.rhandycana.AhorcadoFinal1.model.Usuario;
import com.rhandycana.AhorcadoFinal1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.ok().body("No hay usuarios registrados.");
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{codigoUsuario}")
    public Usuario getUsuarioById(@PathVariable Integer codigoUsuario) {
        return usuarioService.getUsuarioById(codigoUsuario);
    }

    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.createUsuario(usuario);
        return ResponseEntity.ok().body("Usuario agregado exitosamente con ID: " + nuevo.getCodigoUsuario());
    }

    @PutMapping("/{codigoUsuario}")
    public ResponseEntity<?> updateUsuario(@PathVariable Integer codigoUsuario, @RequestBody Usuario usuario) {
        Usuario actualizado = usuarioService.updateUsuario(codigoUsuario, usuario);
        return ResponseEntity.ok().body("Usuario actualizado exitosamente con ID: " + actualizado.getCodigoUsuario());
    }

    @DeleteMapping("/{codigoUsuario}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Integer codigoUsuario) {
        usuarioService.deleteUsuario(codigoUsuario);
        return ResponseEntity.ok().body("Usuario eliminado exitosamente");
    }

    @GetMapping("/")
    public ResponseEntity<?> handleTrailingSlash() {
        return ResponseEntity.badRequest().body("Debes especificar un ID para editar o eliminar, por ejemplo: /api/usuarios/1");
    }

    @PutMapping("/")
    public ResponseEntity<?> handlePutWithoutId() {
        return ResponseEntity.badRequest().body("Debes especificar el ID del usuario a editar, por ejemplo: /api/usuarios/1");
    }

    @DeleteMapping("/")
    public ResponseEntity<?> handleDeleteWithoutId() {
        return ResponseEntity.badRequest().body("Debes especificar el ID del usuario a eliminar, por ejemplo: /api/usuarios/1");
    }
}