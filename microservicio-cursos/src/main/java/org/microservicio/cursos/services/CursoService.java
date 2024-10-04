package org.microservicio.cursos.services;

import org.microservicio.cursos.models.Usuario;
import org.microservicio.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;


public interface CursoService {

    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Optional<Curso> porIdConUsuarios(Long id);
    Curso guardar(Curso usuario);
    void eliminar(Long id);

    void eliminarCursoUsuarioPorId(Long id);

    // Aca se crea el contrato de todos los servicios relacionados al microservicio de Usuarios
    Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);
}
