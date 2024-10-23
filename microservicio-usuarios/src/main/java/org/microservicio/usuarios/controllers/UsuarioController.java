package org.microservicio.usuarios.controllers;

import jakarta.validation.Valid;
import org.microservicio.usuarios.entity.Usuario;
import org.microservicio.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public Map<String, List<Usuario>> listar(){
        return Collections.singletonMap("users",usuarioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Usuario> usuarioOptional = usuarioService.porId(id);
        if (usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result){

        if (result.hasErrors()){
            return validar(result);
        }

        if (!usuario.getEmail().isEmpty() &&
                usuarioService.existePorEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().
                    body(Collections.
                            singletonMap("mensaje","Ya existe un usuario con ese correo electronico!"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){

        if (result.hasErrors()){
            return validar(result);
        }
        
        Optional<Usuario> usr = usuarioService.porId(id);

        if (usr.isPresent()){
            Usuario usuarioModificado = usr.get();

            if (!usuario.getEmail().isEmpty() &&
                    !usuario.getEmail().equalsIgnoreCase(usuarioModificado.getEmail()) &&
                    usuarioService.porEmail(usuario.getEmail()).isPresent()){
                return ResponseEntity.badRequest().
                        body(Collections.
                                singletonMap("mensaje","Ya existe un usuario con ese correo electronico!"));
            }
            usuarioModificado.setNombre(usuario.getNombre());
            usuarioModificado.setEmail(usuario.getEmail());
            usuarioModificado.setPassword(usuario.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardar(usuarioModificado));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioService.porId(id);

        if (usuario.isPresent()){
            usuarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuarios-por-curso")
    public ResponseEntity<?> obtenerUsuariosPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(usuarioService.listarPorIds(ids));

    }
    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errores.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }

}
