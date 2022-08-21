package br.com.tiago.api.services;

import br.com.tiago.api.domain.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario findById(Integer id);
    List <Usuario> findAll();
}
