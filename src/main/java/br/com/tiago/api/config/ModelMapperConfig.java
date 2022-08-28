package br.com.tiago.api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//classe de configuração
public class ModelMapperConfig { /*Essa classe serve para mapear a class domain em DTO*/
    @Bean/*Agora posso fazer Inj de Dep em qualquer lugar do projeto*/
    public ModelMapper mapper(){
        return new ModelMapper();
    }

}

