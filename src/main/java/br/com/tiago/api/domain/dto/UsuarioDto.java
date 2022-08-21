package br.com.tiago.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private Integer id;
    private String nome;
    private String email;
    private String password;
}
