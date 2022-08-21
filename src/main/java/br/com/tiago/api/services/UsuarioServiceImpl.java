package br.com.tiago.api.services;

import br.com.tiago.api.domain.Usuario;
import br.com.tiago.api.repositories.UsuarioRepository;
import br.com.tiago.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
                    /*A Classe Service trabalha com a classe Model e a Repository*/
@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario findById(Integer id) {
        Optional<Usuario> obj = usuarioRepository.findById(id);
       // return obj.orElse(null);/*Se não achar o objeto retorna null*/
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
        /*retorna o objeto ou retorna uma excessão*/
    }

    public List <Usuario> findAll(){
        return usuarioRepository.findAll();
    }
}
