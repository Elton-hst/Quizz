package br.com.mjv.quizz.infrastructure.web.docs;

import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.domain.user.dto.CreateUpdateUserDto;
import br.com.mjv.quizz.domain.config.result.ReturnWithMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Locale;

@Tag(name = "User")
public interface UserControllerDocs {

    @Operation(summary = "cria um novo usuário", method ="POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar um novo usuário"),
    })
    ResponseEntity<User> createUser(CreateUpdateUserDto userDto, UriComponentsBuilder uriBuilder, final Locale locale) throws Exception;

//    @Operation(summary = "Atualiza os dados do usuário", method ="PUT")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Usuário atualizado com sucesso"),
//            @ApiResponse(responseCode = "400", description = "Erro ao tentar atualizar o usuário"),
//    })
//    ReturnWithMessage updateUser(CreateUpdateUserDto userDto);
//
//    @Operation(summary = "Busca um usuário por Id", method ="GET")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Usuário encontrado com sucesso"),
//            @ApiResponse(responseCode = "400", description = "Usuário não encontrado"),
//    })
//    ReturnWithMessage findUserById(UUID id);
//
//    @Operation(summary = "Buca todos os usuários", method ="GET")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Lista de usuários gerada com sucesso"),
//            @ApiResponse(responseCode = "400", description = "Erro ao gerar a lista de usuários"),
//    })
//    List<ReturnWithMessage> findAllUsers();
//
//    @Operation(summary = "Deleção de usuário", method ="DELETE")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Usuário deletado"),
//            @ApiResponse(responseCode = "400", description = "Erro ao deletar o usuário"),
//    })
//    void deleteUserById(UUID id);

}
