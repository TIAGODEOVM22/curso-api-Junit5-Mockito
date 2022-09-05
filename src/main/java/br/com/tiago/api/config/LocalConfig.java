package br.com.tiago.api.config;

import br.com.tiago.api.domain.Usuario;
import br.com.tiago.api.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local") /*indica para o application.properties que esta como local*/
public class LocalConfig {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean /*sobe este metodo toda vez que o perfil estiver ativo*/
    public void startDB(){
        Usuario u1 = new Usuario(null, "Tiago", "tiago@mail.com", "123");
        Usuario u2 = new Usuario(null, "Davi", "davi@mail.com", "321");

        usuarioRepository.saveAll(List.of(u1,u2));
    }


}
