package com.ahorcado.modelo;

import com.ahorcado.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int resp;

    public List listar() {
        String sql = "SELECT * FROM usuarios;";
        List<Usuarios> listaUsuarios = new ArrayList<>();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuarios usuario = new Usuarios();
                usuario.setCodigoUsuario(rs.getInt("codigo_usuario"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setApellidoUsuario(rs.getString("apellido_usuario"));
                usuario.setCorreoUsuario(rs.getString("correo_usuario"));
                usuario.setContraseñaUsuario(rs.getString("contraseña_usuario"));
                usuario.setFechaRegistro(rs.getTimestamp("fecha_registro"));
                listaUsuarios.add(usuario);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaUsuarios;
    }

    public int agregar(Usuarios usuario) {
        String sql = "INSERT INTO usuarios (nombre_usuario, apellido_usuario, correo_usuario, contraseña_usuario, fecha_registro) VALUES (?, ?, ?, ?, NOW())";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getApellidoUsuario());
            ps.setString(3, usuario.getCorreoUsuario());
            ps.setString(4, usuario.getContraseñaUsuario());
            resp = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    public Usuarios buscar(int id) {
        String sql = "SELECT * FROM usuarios WHERE codigo_usuario = ?";
        Usuarios usuario = null;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                usuario = new Usuarios();
                usuario.setCodigoUsuario(rs.getInt("codigo_usuario"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setApellidoUsuario(rs.getString("apellido_usuario"));
                usuario.setCorreoUsuario(rs.getString("correo_usuario"));
                usuario.setContraseñaUsuario(rs.getString("contraseña_usuario"));
                usuario.setFechaRegistro(rs.getTimestamp("fecha_registro"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public Usuarios validar(String correo, String password) {
        String sql = "SELECT * FROM usuarios WHERE correo_usuario = ? AND contraseña_usuario = ?";
        Usuarios usuario = null;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, password);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                usuario = new Usuarios();
                usuario.setCodigoUsuario(rs.getInt("codigo_usuario"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setApellidoUsuario(rs.getString("apellido_usuario"));
                usuario.setCorreoUsuario(rs.getString("correo_usuario"));
                usuario.setContraseñaUsuario(rs.getString("contraseña_usuario"));
                usuario.setFechaRegistro(rs.getTimestamp("fecha_registro"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
}