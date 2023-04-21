package org.falenda.recursos;

import org.falenda.models.Usuario;
import org.falenda.util.ConexionBaseDatos;

import java.lang.reflect.WildcardType;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorioImpl implements Repositorio<Usuario> {

    @Override
    public List<Usuario> listar() {
        int contFilasResulset = 0;
        List<Usuario> listaUsuarios = new ArrayList<>();

        try (Connection cn = ConexionBaseDatos.conectar();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM usuarios")) {


            while (rs.next()) {
                contFilasResulset++;
                Usuario us = new Usuario();
                us.setId(rs.getInt("id"));
                us.setUsername(rs.getString("username"));
                us.setPassword(rs.getString("password"));
                us.setEmail(rs.getString("email"));

                listaUsuarios.add(us);
            }

            if (contFilasResulset == 0) {
                System.out.println("No hay ningún Registro");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaUsuarios;
    }

    @Override
    public Usuario buscarId(Integer id) {
        Usuario us = new Usuario();


        try (Connection cn = ConexionBaseDatos.conectar();
             PreparedStatement pst = cn.prepareStatement("SELECT * FROM usuarios WHERE id=?")) {

            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    us.setId(rs.getInt("id"));
                    us.setUsername(rs.getString("username"));
                    us.setPassword(rs.getString("password"));
                    us.setEmail(rs.getString("email"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return us;
    }

    @Override
    public String agregarUsuario(Usuario usuario) {
        String sql;

        boolean actualiza = false, inserta = false;

        if (usuario.getId() != null && usuario.getId() > 0) {
            if (this.buscarId(usuario.getId()).getId().equals(usuario.getId())) {
                sql = "UPDATE usuarios SET username=?, password=?, email=? WHERE id=?";
                actualiza = true;
            } else {
                return "El ID ingresado no se corresponde con ningún usuario de la BBDD";
            }
        } else {
            sql = "INSERT INTO usuarios (username, password, email) VALUES (?,?,?)";
            inserta = true;
        }

        try (Connection cn = ConexionBaseDatos.conectar();
             PreparedStatement st = cn.prepareStatement(sql)) {

            st.setString(1, usuario.getUsername());
            st.setString(2, usuario.getPassword());
            st.setString(3, usuario.getEmail());

            if (actualiza) {
                st.setInt(4, usuario.getId());
            }

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (actualiza) {
            return "Usuario " + usuario.getUsername() + " actualizado correctamente";
        } else if (inserta) {
            return "Usuario " + usuario.getUsername() + " agregado correctamente";
        }
        return "error";
    }

    @Override
    public void eliminarUsuario(Integer id) {

        try (Connection cn = ConexionBaseDatos.conectar();
             PreparedStatement pst = cn.prepareStatement("DELETE FROM usuarios WHERE id=?")) {

            pst.setInt(1, id);
            pst.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
