package br.com.mjv.quizz.application.useCases.user;

import br.com.mjv.quizz.domain.config.result.Result;
import br.com.mjv.quizz.domain.config.result.ReturnWithMessage;
import br.com.mjv.quizz.domain.config.validator.Validator;
import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.domain.user.dto.CreateUpdateUserDto;
import br.com.mjv.quizz.domain.user.dto.validator.UserValidate;
import br.com.mjv.quizz.domain.user.repository.UsersRepository;
import br.com.mjv.quizz.infrastructure.configs.exception.UserException;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.ChatContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UsersRepository repository;
    private final ResourceBundle messageBundle;

    private String getMessage(String key, Locale locale) {
        return ResourceBundle.getBundle(messageBundle.getBaseBundleName(), locale).getString(key);
    }

    public ReturnWithMessage<User> execute(CreateUpdateUserDto userDto, Locale locale) {
        Validator.validate(new UserValidate(), userDto);

        User user = new User(userDto.name(), userDto.phone(),userDto.score(), ChatContext.INITIALIZED_CHAT);
        var result = add(user);

        return result.ifSuccess(user1 -> {
            return new ReturnWithMessage<User>(getMessage("users.create.success", locale), user1);
        }).throwsEarlyIf(RuntimeException.class, error -> {
            return new RuntimeException(getMessage("users.create.fail", locale), error);
        }).execute().orElseThrow(() -> {
            return new UserException(getMessage("users.create.fail", locale));
        });
    }

    private Result<RuntimeException, User> add(User user) {
        var result = repository.add(user).orElse(null);

        if(Objects.isNull(result)) {
            return Result.failWithProblem(new RuntimeException());
        } else {
            return Result.successWithReturn(result);
        }
    }
}
