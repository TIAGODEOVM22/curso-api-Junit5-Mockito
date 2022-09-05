package br.com.tiago.api.resources;

import br.com.tiago.api.domain.Usuario;
import br.com.tiago.api.domain.dto.UsuarioDto;
import br.com.tiago.api.services.UsuarioServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UsuarioResourceTest {

    public static final Integer ID = 1;
    public static final String NOME = "tiago";
    public static final String EMAIL = "tiago@gmail.com";
    public static final String PASSWORD = "123";

    private Usuario usuario = new Usuario();
    private UsuarioDto dto = new UsuarioDto();

    private void startUsuario(){
        /*start das instâncias de Usuario e UsuarioDTO*/
        usuario = new Usuario(ID, NOME, EMAIL, PASSWORD);
        dto = new UsuarioDto(ID, NOME, EMAIL, PASSWORD);
    }

    @InjectMocks /*em formato de Bean*/
    private UsuarioResource resource;

    @Mock
    private UsuarioServiceImpl service;

    @Mock
    private ModelMapper mapper;


    @BeforeEach
    void setUp() {
        /*inicia os mocks DESTA classe e chama o metodo startUsuario*/
        MockitoAnnotations.openMocks(this);
        startUsuario();
    }

    @Test /*QuandoChamarFindByIdretorneSucesso*/
    void whenFindByIdThenReturnSuccess() {
        /*QuandoChamarFindByIdEmQualquerValorInteiroRetorneUsuario*/
        Mockito.when(service.findById(Mockito.anyInt())).thenReturn(usuario);

        /*QuandoTentarConverterQUalquerEntidadeParaDtoRetorneDTO*/
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(dto);

        /*o retorno do metodo da controller é do tipo ResponseEntity*/
        ResponseEntity<UsuarioDto> response = resource.findById(ID);

        /*__________VALIDAÇÕES__________*/
        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(UsuarioDto.class, response.getBody().getClass());

        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(NOME, response.getBody().getNome());
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());
        Assertions.assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test /*quandoBuscarTodosretorneUmaListaDeUserDto*/
    void WhenFindAllThenReturnlistOfUerDto() {
        Mockito.when(service.findAll()).thenReturn(List.of(usuario));
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(dto);

        ResponseEntity<List<UsuarioDto>> response = resource.findAll();

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(ArrayList.class, response.getBody().getClass());
        Assertions.assertEquals(UsuarioDto.class, response.getBody().get(0).getClass());

        /*assegura que é o mesmo ID que passei como parametro*/
        Assertions.assertEquals(ID, response.getBody().get(0).getId());
        Assertions.assertEquals(NOME, response.getBody().get(0).getNome());
        Assertions.assertEquals(EMAIL, response.getBody().get(0).getEmail());
        Assertions.assertEquals(PASSWORD, response.getBody().get(0).getPassword());



    }

    @Test
    void whenCreateThenReturnCreated() {
        Mockito.when(service.create(Mockito.any())).thenReturn(usuario);/*tem que ser a classe UsuarioModel*/

        ResponseEntity<UsuarioDto> response = resource.create(dto);

        /*assegura que é ResponseEntity*/
        Assertions.assertEquals(ResponseEntity.class, response.getClass());

        /*assegura que o Status é Created*/
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        /*assegura que no Headers tem a uri de acesso LOCATION*/
        Assertions.assertNotNull( response.getHeaders().get("Location"));




    }

    @Test
    void whenUpdateThenReturnSucess() {
        Mockito.when(service.update(dto)).thenReturn(usuario);
        Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(dto);

        ResponseEntity<UsuarioDto> response = resource.update(ID, dto);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(UsuarioDto.class, response.getBody().getClass());


        Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(NOME, response.getBody().getNome());
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());

    }

    @Test
    void whenDeleteThenReturnSuccess() {

        Mockito.doNothing().when(service).delete(Mockito.anyInt());

        ResponseEntity<UsuarioDto> response = resource.delete(ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(ResponseEntity.class, response.getClass());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        Mockito.verify(service, Mockito.times(1)).delete(Mockito.anyInt());

    }
}