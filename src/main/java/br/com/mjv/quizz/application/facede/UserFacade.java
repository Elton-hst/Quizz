package br.com.mjv.quizz.application.facede;

import br.com.mjv.quizz.application.useCases.user.CreateUserUseCase;
import br.com.mjv.quizz.application.useCases.user.FindUserUseCase;
import br.com.mjv.quizz.application.useCases.user.UpdateUserUseCase;
import br.com.mjv.quizz.domain.config.result.ReturnWithMessage;
import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.domain.user.dto.CreateUpdateUserDto;
import br.com.mjv.quizz.domain.user.dto.GetUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final CreateUserUseCase createUserUseCase;
    private final FindUserUseCase findUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    public User createUser(CreateUpdateUserDto userDto, Locale locale) {
        return createUserUseCase.execute(userDto, locale);
    }

    public User findUser(GetUserDto userDto) {
        return findUserUseCase.execute(userDto);
    }

    public User updateUser(CreateUpdateUserDto userDto, Locale locale) {
        return updateUserUseCase.execute(userDto, locale);
    }
}
