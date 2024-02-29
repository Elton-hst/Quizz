package br.com.mjv.quizz.application.useCases.user;

import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.domain.user.dto.CreateUpdateUserDto;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.ChatContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Slf4j
public class UserManagementUseCase {

    private final UpdateUserUseCase updateUser;

    public UserManagementUseCase(UpdateUserUseCase updateUser) {
        this.updateUser = updateUser;
    }

    public String initializedChat(User user) {
        log.info("Initialized chat");

        var result = updateUser(user.phone(), ChatContext.START_OR_CREATE);

        String message = String.format(
                "Olá, bem vindo(a) %s ao Quizz \n" +
                        "\nO que deseja fazer: \n" +
                        "1 - jogar uma partida \n" +
                        "2 - criar uma partida",
                result.name());

        return message;
    }

    public User updateUser(String phone, ChatContext context) {
        CreateUpdateUserDto userDto = new CreateUpdateUserDto(phone, context);
        return updateUser.execute(userDto, Locale.ENGLISH);
    }

    public User updateUser(String phone, Double score) {
        CreateUpdateUserDto userDto = new CreateUpdateUserDto(phone, score);
        return updateUser.execute(userDto, Locale.ENGLISH);
    }

}
