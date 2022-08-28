package br.com.tiago.api.services;

import br.com.tiago.api.domain.Usuario;
import br.com.tiago.api.domain.dto.UsuarioDto;
import br.com.tiago.api.repositories.UsuarioRepository;
import br.com.tiago.api.services.exceptions.DataIntegratyViolationException;
import br.com.tiago.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
class UsuarioServiceImplTest {

    public static final Integer ID = 1;
    public static final String NOME = "tiago";
    public static final String EMAIL = "tiago@gmail.com";
    public static final String PASSWORD = "123";


    @InjectMocks /*cria uma instancia real*/
    private UsuarioServiceImpl service;
    @Mock/*moka essa instancia*/
    private UsuarioRepository repository;
    @Mock
    private ModelMapper mapper;

    private Usuario usuario;
    private UsuarioDto dto;
    private Optional<Usuario> optionalUsuario;

    @BeforeEach/*antes de tudo vai realizar esse metodo*/
    void setUp() {
        MockitoAnnotations.openMocks(this);/*this faz referencia a esta classe que estams testando*/
        startUsuario();
    }

    @Test /*Aqui renomei o metodo findbyid*/
    void whenFindByIdThenReturnAnUserInstance() {
        /*quando o findbyid for chamado entao retorne o optional usuario*/
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUsuario);

        /*faz a chamada para testar o findbyid*/
        Usuario response = service.findById(ID);

        /*verifica se não é nulo*/
        Assertions.assertNotNull(response);

        /*assegura que o objeto retornado será do tipo usuario Model*/
        Assertions.assertEquals(Usuario.class, response.getClass());

        /*faz a comparação de ID entre os dois objetos*/
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NOME, response.getNome());
        Assertions.assertEquals(EMAIL, response.getEmail());

    }
    @Test
    /*quando buscar por id retorne uma excessão de objeto não encontrado*/
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        Mockito.when(repository.findById(Mockito.anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
            try{/*tenta chamar o service.findById*/
                service.findById(ID);
            }catch (Exception ex){ /*se pegar uma execessão garante que ela seja da classe Exception*/
                /*garante que é a mesma classe que estoura na excessão*/
                Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
                /*garante mesma mensagem da excessão*/
                Assertions.assertEquals("Objeto não encontrado", ex.getMessage());

            }


    }

    @Test
     /*Nome do metodo alterado*/
    /*void findAll()*/
    void whenFindAllThenReturnAnListOfUsers(){/*quando buscar todos retorne uma lista de users*/
        Mockito.when(repository.findAll()).thenReturn(List.of(usuario));

        List<Usuario> response = service.findAll();

        Assertions.assertNotNull(response);
        /*Assegura que não é nulo*/

        Assertions.assertEquals(1,response.size());
        /*assegura que a lista é de apenas um usuario*/

        Assertions.assertEquals(Usuario.class, response.get(0).getClass());
        /*assegura que o obj de indice 0 é do tipo user*/

        Assertions.assertEquals(ID, response.get(0).getId());
        /*Assegura que o id do obj é igual ao que esta vindo na variavel*/

        Assertions.assertEquals(NOME, response.get(0).getNome());
        Assertions.assertEquals(EMAIL, response.get(0).getEmail());
        Assertions.assertEquals(PASSWORD, response.get(0).getPassword());


    }

    @Test
    void whenCreateThenReturnSucess() {/*quando criar retorne sucesso*/

        /* primeiro moka o retorno do metodo create da classe SERVICE*/
        Mockito.when(repository.save(any())).thenReturn(usuario);
        /*se alguem alterar o retorno do metodo para usuarioDTO
        o teste vai dar erro*/

        /*segundo faço a chamada para o metodo create*/
        Usuario response = service.create(dto);


        Assertions.assertNotNull(response);/*assegura que não será nula a resposta*/
        Assertions.assertEquals(Usuario.class, response.getClass());/*assegura ser igual a classe*/
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NOME, response.getNome());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(PASSWORD, response.getPassword());


    }
    @Test
    /*cenario de insucesso ao criar usuario*/
    void whenCreateThenReturnAnDataIntegrityViolationException() {

        /* primeiro moka o retorno do metodo create da classe SERVICE*/
        Mockito.when(repository.findByEmail(anyString())).thenReturn(optionalUsuario);

      try{
          optionalUsuario.get().setId(2);/*tive que alterar o id*/
          service.create(dto);
      }catch(Exception ex){
          Assertions.assertEquals(DataIntegratyViolationException.class, ex.getClass());
          Assertions.assertEquals("Email já cadastrado!" , ex.getMessage());
          /*a msg dessa ex é igual a msg do findByEmail da classe service*/


      }

    }


        @Test
        void whenUpdateTheReturnSuccess(){

        Mockito.when(repository.save(any())).thenReturn(usuario);

        Usuario response = service.update(dto);


        Assertions.assertNotNull(response);/*assegura que não será nula a resposta*/
        Assertions.assertEquals(Usuario.class, response.getClass());/*assegura ser igual a classe*/
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NOME, response.getNome());
        Assertions.assertEquals(EMAIL, response.getEmail());
        Assertions.assertEquals(PASSWORD, response.getPassword());


    }

    @Test
    void whenUpdateThenReturnAnDataIntegrityViolationException() {

        /* primeiro moka o retorno do metodo create da classe SERVICE*/
        Mockito.when(repository.findByEmail(anyString())).thenReturn(optionalUsuario);

        try{
            optionalUsuario.get().setId(2);/*tive que alterar o id*/
            service.create(dto);
        }catch(Exception ex){
            Assertions.assertEquals(DataIntegratyViolationException.class, ex.getClass());
            Assertions.assertEquals("Email já cadastrado!" , ex.getMessage());
            /*a msg dessa ex é igual a msg do findByEmail da classe service*/


        }

    }

    @Test
    void deleteWithSucess() {
        Mockito.when(repository.findById(anyInt())).thenReturn(optionalUsuario);
        Mockito.doNothing().when(repository).deleteById(anyInt());
        service.delete(ID);
        Mockito.verify(repository, Mockito.times(1)).deleteById(anyInt());

    }

    @Test
    void deleteWithObjectNotFoundException() {
        Mockito.when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException("Obejto não encontrado"));

        try {
            service.delete(ID);
        } catch (Exception ex){
            Assertions.assertEquals(ObjectNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Obejto não encontrado", ex.getMessage());
        }
    }

    private void startUsuario(){
        usuario = new Usuario(ID, NOME, EMAIL, PASSWORD);
        dto = new UsuarioDto(ID, NOME, EMAIL, PASSWORD);
        optionalUsuario = Optional.of(new Usuario(ID, NOME, EMAIL, PASSWORD));
            /*optional de usuario*/
    }
}