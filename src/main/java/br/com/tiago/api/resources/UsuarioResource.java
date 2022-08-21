package br.com.tiago.api.resources;

import br.com.tiago.api.domain.Usuario;
import br.com.tiago.api.domain.dto.UsuarioDto;
import br.com.tiago.api.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioDto> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(mapper.map(usuarioService.findById(id), UsuarioDto.class));
        /*o mapper.map pede o objeto que tem a fonte das informações e
        qual o destino dessas informaçoes*/
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDto>> findAll(){

        List<Usuario> list = usuarioService.findAll();
        List<UsuarioDto> listDTO = list.stream().map(x -> mapper.map(x, UsuarioDto.class)).collect(Collectors.toList());
        return ResponseEntity.ok().body(listDTO);
    }
}
