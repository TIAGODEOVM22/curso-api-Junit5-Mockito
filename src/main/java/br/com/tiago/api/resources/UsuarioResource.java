package br.com.tiago.api.resources;

import br.com.tiago.api.domain.Usuario;
import br.com.tiago.api.domain.dto.UsuarioDto;
import br.com.tiago.api.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        List<UsuarioDto> listDTO = list.stream()
                .map(x -> mapper.map(x, UsuarioDto.class)).collect(Collectors.toList());
            /*para cada obj X sera mapeado para DTO e depois coletado e tranf em uma lista */
        return ResponseEntity.ok().body(listDTO);
    }

    @PostMapping
    public ResponseEntity<UsuarioDto> create(@RequestBody UsuarioDto obj){
        Usuario newObj = usuarioService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).build();
        /*status created retorna 201
        * porém preciso criar uma URI de acesso ao novo OBJETO*/

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioDto>update(@PathVariable Integer id, @RequestBody UsuarioDto obj){
    obj.setId(id);/*só para garantir que é o mesmo id que veio na url*/
    Usuario newObj = usuarioService.update(obj);
    return ResponseEntity.ok().body(mapper.map(newObj, UsuarioDto.class));

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UsuarioDto> delete(@PathVariable Integer id){
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
