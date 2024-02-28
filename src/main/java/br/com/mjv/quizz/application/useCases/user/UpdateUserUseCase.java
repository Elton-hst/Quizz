package br.com.mjv.quizz.application.useCases.user;

import br.com.mjv.quizz.domain.config.result.Result;
import br.com.mjv.quizz.domain.config.result.ReturnWithMessage;
import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.domain.user.dto.CreateUpdateUserDto;
import br.com.mjv.quizz.domain.user.repository.UsersRepository;
import br.com.mjv.quizz.infrastructure.configs.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateUserUseCase {

    private final UsersRepository repository;
    private final ResourceBundle messageBundle;

    public UpdateUserUseCase(UsersRepository repository, ResourceBundle messageBundle) {
        this.repository = repository;
        this.messageBundle = messageBundle;
    }
    private String getMessage(String key, Locale locale) {
        return ResourceBundle.getBundle(messageBundle.getBaseBundleName(), locale).getString(key);
    }

    public ReturnWithMessage<User> execute(CreateUpdateUserDto userDto, Locale locale) throws Exception {
        User newUser = new User(userDto.name(), userDto.phone(), userDto.score(), userDto.context());

        var result = update(newUser);

        return result.ifSuccess(user1 -> {
            return new ReturnWithMessage<User>(getMessage("users.update.success", locale), user1);
        }).throwsEarlyIf(RuntimeException.class, error -> {
            return new RuntimeException(getMessage("users.update.fail", locale), error.getCause());
        }).execute().orElseThrow(() -> {
            return new UserException(getMessage("users.update.fail", locale));
        });
    }

    private Result<RuntimeException, User> update(User user) {
        var result = repository.update(user).orElse(null);

        if (Objects.isNull(result)) {
            return Result.failWithProblem(new RuntimeException());
        } else {
            return Result.successWithReturn(result);
        }
    }

}
