package org.microservicio.usuarios.services;

import org.microservicio.usuarios.clients.CursoClientRest;
import org.microservicio.usuarios.entity.Usuario;
import org.microservicio.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private CursoClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return (List<Usuario>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        repository.deleteById(id);
        client.eliminarCursoUsuarioPorId(id);
    }

    @Override
    public Optional<Usuario> porEmail(String email) {
        return repository.porEmail(email);
    }

    @Override
    public boolean existePorEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    @Transactional
    public List<Usuario> listarPorIds(Iterable<Long> ids) {
        return (List<Usuario>) repository.findAllById(ids);
    }
}
