package br.com.tiago.api.services;

import br.com.tiago.api.domain.Usuario;
import br.com.tiago.api.domain.dto.UsuarioDto;

import java.util.List;

public interface UsuarioService {

    Usuario findById(Integer id);
    List <Usuario> findAll();
    Usuario create(UsuarioDto obj);
    Usuario update(UsuarioDto obj);
    void delete(Integer id);
}
