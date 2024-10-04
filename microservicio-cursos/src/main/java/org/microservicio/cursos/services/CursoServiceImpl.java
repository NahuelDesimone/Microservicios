package org.microservicio.cursos.services;

import org.microservicio.cursos.clients.UsuarioClientRest;
import org.microservicio.cursos.models.Usuario;
import org.microservicio.cursos.models.entity.Curso;
import org.microservicio.cursos.models.entity.CursoUsuario;
import org.microservicio.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepository repository;

    @Autowired
    private UsuarioClientRest clienteUsuario;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Curso> porIdConUsuarios(Long id) {
        Optional<Curso> o = repository.findById(id);
        if (o.isPresent()){
            Curso curso = o.get();
            if (!curso.getCursoUsuarios().isEmpty()){
                List<Long> ids = curso.getCursoUsuarios().stream().map(cu -> cu.getUsuarioId())
                        .toList();

                List<Usuario> usuarios = clienteUsuario.obtenerUsuariosPorCurso(ids);
                curso.setUsuarios(usuarios);
            }

            return Optional.of(curso);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Curso guardar(Curso usuario) {
        return repository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        repository.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {

        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()){
            Usuario usuarioMicroservicio = clienteUsuario.detalle(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMicroservicio.getId());
            curso.addCursoUsuario(cursoUsuario);

            repository.save(curso);
            return Optional.of(usuarioMicroservicio);

        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()){
            Usuario usuarioMicroservicio = clienteUsuario.crear(usuario);

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMicroservicio.getId());
            curso.addCursoUsuario(cursoUsuario);

            repository.save(curso);
            return Optional.of(usuarioMicroservicio);

        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {

        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()){
            Usuario usuarioMicroservicio = clienteUsuario.detalle(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMicroservicio.getId());
            curso.removeCursoUsuario(cursoUsuario);

            repository.save(curso);
            return Optional.of(usuarioMicroservicio);

        }
        return Optional.empty();
    }
}
