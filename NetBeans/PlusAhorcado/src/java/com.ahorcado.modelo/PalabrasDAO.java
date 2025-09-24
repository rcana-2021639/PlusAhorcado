package com.ahorcado.modelo;

import com.ahorcado.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PalabrasDAO {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int resp;

    public List listar() {
        String sql = "SELECT * FROM palabras;";
        List<Palabras> listaPalabras = new ArrayList<>();
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Palabras palabra = new Palabras();
                palabra.setCodigoPalabra(rs.getInt("codigo_palabra"));
                palabra.setPalabra(rs.getString("palabra"));
                palabra.setPista(rs.getString("pista"));
                palabra.setCategoria(rs.getString("categoria"));
                listaPalabras.add(palabra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPalabras;
    }

    public Palabras obtenerPalabraRandom() {
        String sql = "CALL sp_obtenerpalabrarandom();";
        Palabras palabra = null;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                palabra = new Palabras();
                palabra.setCodigoPalabra(rs.getInt("codigo_palabra"));
                palabra.setPalabra(rs.getString("palabra"));
                palabra.setPista(rs.getString("pista"));
                palabra.setCategoria(rs.getString("categoria"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return palabra;
    }

    public int agregar(Palabras palabra) {
        String sql = "INSERT INTO palabras (palabra, pista, categoria) VALUES (?, ?, ?)";
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, palabra.getPalabra());
            ps.setString(2, palabra.getPista());
            ps.setString(3, palabra.getCategoria());
            resp = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resp;
    }

    public Palabras buscar(int id) {
        String sql = "SELECT * FROM palabras WHERE codigo_palabra = ?";
        Palabras palabra = null;
        try {
            con = cn.Conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                palabra = new Palabras();
                palabra.setCodigoPalabra(rs.getInt("codigo_palabra"));
                palabra.setPalabra(rs.getString("palabra"));
                palabra.setPista(rs.getString("pista"));
                palabra.setCategoria(rs.getString("categoria"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return palabra;
    }
}