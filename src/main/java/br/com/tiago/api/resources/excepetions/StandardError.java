package br.com.tiago.api.resources.excepetions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class StandardError {
    /*Essa classe é o erro padrão
    * vamos pegar os atributos do erro...
    * timestamp,status, error, path*/

    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;

}
