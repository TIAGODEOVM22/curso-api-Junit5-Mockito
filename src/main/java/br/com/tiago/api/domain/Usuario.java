package br.com.tiago.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Usuario{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)/*BD gera id automatico*/
    private Integer id;
    private String nome;

    @Column(unique = true)
    private String email;
    private String password;
}
