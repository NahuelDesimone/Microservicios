package org.microservicio.usuarios.services;

import org.microservicio.usuarios.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    Optional<Usuario> porEmail(String email);
    boolean existePorEmail(String name);

    List<Usuario> listarPorIds(Iterable<Long> ids);
}
