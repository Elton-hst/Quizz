package br.com.mjv.quizz.application.useCases.user;

import br.com.mjv.quizz.domain.config.result.Result;
import br.com.mjv.quizz.domain.config.validator.Validator;
import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.domain.user.dto.CreateUpdateUserDto;
import br.com.mjv.quizz.domain.user.dto.validator.UserValidate;
import br.com.mjv.quizz.domain.user.repository.UsersRepository;
import br.com.mjv.quizz.infrastructure.configs.exception.UserException;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.ChatContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
public class CreateUserUseCase {

    private final UsersRepository repository;
    private final ResourceBundle messageBundle;

    public CreateUserUseCase(UsersRepository repository, ResourceBundle messageBundle) {
        this.repository = repository;
        this.messageBundle = messageBundle;
    }

    private String getMessage(String key, Locale locale) {
        return ResourceBundle.getBundle(messageBundle.getBaseBundleName(), locale).getString(key);
    }

    public User execute(CreateUpdateUserDto userDto, Locale locale) {
        Validator.validate(new UserValidate(), userDto);
        User user = new User(userDto.name(), userDto.phone(),userDto.score(), ChatContext.INITIALIZED_CHAT);
        return result(user, locale);
    }

    public User execute(CreateUpdateUserDto userDto) {
        Validator.validate(new UserValidate(), userDto);
        User user = new User(userDto.name(), userDto.phone(),userDto.score(), ChatContext.INITIALIZED_CHAT);
        return result(user, Locale.ENGLISH);
    }

    public User result(User user, Locale locale) {
        var result = add(user);
        return result.ifSuccess(user1 -> {
            log.info("User -> phone {}, status: {}", user.phone(), getMessage("users.create.success", locale));
            return user1;
        }).throwsEarlyIf(RuntimeException.class, error -> {
            log.info("User -> phone: {}, status: {}", user.phone(), getMessage("users.create.fail", locale));
            return new UserException("Error create user");
        }).execute();
    }

    private Result<User, RuntimeException> add(User user) {
        return repository.add(user)
                .map(Result::<User, RuntimeException>successWithReturn)
                .orElse(Result.failWithProblem(new UserException("Error create user")));
    }
}
