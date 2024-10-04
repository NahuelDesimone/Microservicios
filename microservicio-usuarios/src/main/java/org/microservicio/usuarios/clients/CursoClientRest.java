package org.microservicio.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Esta interfaz es la que se encarga de comunicarse con el microservicio de cursos y consumir todos sus servicios
@FeignClient(name = "microservicio-cursos", url = "localhost:8002")
public interface CursoClientRest {

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    void eliminarCursoUsuarioPorId(@PathVariable Long id);

}
