package org.microservicio.cursos.services;

import org.microservicio.cursos.entity.Curso;

import java.util.List;
import java.util.Optional;


public interface CursoService {

    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Curso guardar(Curso usuario);
    void eliminar(Long id);
}
