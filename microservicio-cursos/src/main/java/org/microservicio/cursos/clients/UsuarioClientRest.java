package org.microservicio.cursos.clients;

import org.microservicio.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//Esta interfaz es la que se encarga de comunicarse con el microservicio de usuarios y consumir todos sus servicios
@FeignClient(name = "microservicio-usuarios", url = "microservicio-usuarios:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping
    Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/usuarios-por-curso")
    List<Usuario> obtenerUsuariosPorCurso(@RequestParam Iterable<Long> ids);


}
