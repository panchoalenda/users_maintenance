package org.falenda.recursos;

import java.util.List;

public interface Repositorio<T> {
    /* Listar */
    List<T> listar();

    /* Buscar por ID */
    T buscarId(Integer id);

    /* Agregar y Actualizar*/
    String agregarUsuario(T t);

    /* Eliminar */
    void eliminarUsuario(Integer id);

}
