package org.falenda;

import org.falenda.models.Usuario;
import org.falenda.recursos.Repositorio;
import org.falenda.recursos.UsuarioRepositorioImpl;

import javax.swing.*;
import java.util.HashMap;

public class ElemploUsuarioModi {
    public static void main(String[] args) {

        Repositorio<Usuario> repositorio = new UsuarioRepositorioImpl();

        HashMap<String, Integer> operaciones = new HashMap<>();

        operaciones.put("Actualizar", 1);
        operaciones.put("Eliminar", 2);
        operaciones.put("Agregar", 3);
        operaciones.put("Listar", 4);
        operaciones.put("Buscar por ID", 5);
        operaciones.put("Salir", 6);

        Object[] opArreglo = operaciones.keySet().toArray();

        int auxiliar = 0;
        do {
            Object opcion = JOptionPane.showInputDialog(null, "Seleccione un Operación", "Mantenedor de Usuarios", JOptionPane.INFORMATION_MESSAGE, null, opArreglo, opArreglo[1]);

            if (opcion == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una operación");
            } else {
                Integer opcionIndice = operaciones.get(opcion.toString());

                switch (opcionIndice) {
                    case 1 -> {
                        Usuario usuario = new Usuario();
                        try {
                            usuario.setId(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del Usuario a modificar")));
                            usuario.setUsername(JOptionPane.showInputDialog("Ingrese nombre de usuario"));
                            usuario.setPassword(JOptionPane.showInputDialog("Ingrese contraseña"));
                            usuario.setEmail(JOptionPane.showInputDialog("Ingrese email"));
                            String msj = repositorio.agregarUsuario(usuario);
                            System.out.println(msj);
                            JOptionPane.showMessageDialog(null, msj);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Debe ingresar un ID valido");
                        }
                    }
                    case 2 -> {
                        Usuario usuario = new Usuario();

                        try {
                            usuario.setId(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID del Usuario a eliminar")));
                            int val = JOptionPane.showInternalConfirmDialog(null, "¿Está seguro de ELIMINAR el usuario con ID: " + usuario.getId() + " ?");
                            if (val == 0) {
                                repositorio.eliminarUsuario(usuario.getId());
                                System.out.println("El usuario " + usuario.getId() + " se ELIMINÓ correctamente");
                                JOptionPane.showMessageDialog(null, "El usuario " + usuario.getId() + " se ELIMINÓ correctamente");
                            }

                        } catch (NumberFormatException e) {
                            System.err.println("Debe ingresar un ID válido");

                        }

                    }
                    case 3 -> {
                        Usuario usuario = new Usuario();
                        JOptionPane.showMessageDialog(null, "A continuación se le solicitará los datos para el nuevo usuario");
                        usuario.setUsername(JOptionPane.showInputDialog("Ingrese nombre de usuario"));
                        usuario.setPassword(JOptionPane.showInputDialog("Ingrese contraseña"));
                        usuario.setEmail(JOptionPane.showInputDialog("Ingrese email"));
                        if (!usuario.getUsername().equals("") || !usuario.getPassword().equals("") || !usuario.getEmail().equals("")) {
                            String msj = repositorio.agregarUsuario(usuario);
                            System.out.println(msj);
                            JOptionPane.showMessageDialog(null, msj);
                        } else {
                            System.out.println("El nombre de usuario, la contraseña y el email no pueden estar vacíos");
                            JOptionPane.showMessageDialog(null, "El nombre de usuario, la contraseña y el email no pueden estar vacíos");
                        }
                    }
                    case 4 -> {
                        System.out.println("=================LISTAR==================");
                        repositorio.listar().forEach(System.out::println);
                    }
                    case 5 -> {
                        System.out.println("=================BUSCAR POR ID==================");
                        Usuario us = new Usuario();
                        try {
                            us.setId(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el ID de usuario a buscar")));
                            System.out.println(repositorio.buscarId(us.getId()));
                            JOptionPane.showMessageDialog(null, repositorio.buscarId(us.getId()));
                        } catch (NumberFormatException e) {
                            System.err.println("Debe ingresar iun ID válido (Ejemplo 1) ");

                        }
                    }

                    case 6 -> {
                        JOptionPane.showMessageDialog(null, "Hasta pronto!!");
                        auxiliar = 1;
                    }

                }
            }

        } while (auxiliar != 1);
    }

}

