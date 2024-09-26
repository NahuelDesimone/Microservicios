package org.microservicio.cursos.repositories;

import org.microservicio.cursos.models.entity.Curso;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository <Curso, Long> {
}
