package br.com.tiago.api.services;

import br.com.tiago.api.domain.Usuario;

public interface UsuarioService {

    Usuario findById(Integer id);
}
