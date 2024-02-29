package br.com.mjv.quizz.application.useCases.user;

import br.com.mjv.quizz.domain.config.result.Result;
import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.domain.user.dto.CreateUpdateUserDto;
import br.com.mjv.quizz.domain.user.repository.UsersRepository;
import br.com.mjv.quizz.infrastructure.configs.exception.UserException;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
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

    public User execute(CreateUpdateUserDto userDto) {
        User user = new User(userDto.name(), userDto.phone(), userDto.score(), userDto.context());
        return result(user, Locale.ENGLISH);
    }
    public User execute(CreateUpdateUserDto userDto, Locale locale) {
        User user = new User(userDto.name(), userDto.phone(), userDto.score(), userDto.context());
        return result(user, locale);
    }

    public User result(User user, Locale locale) {
        var result = updateUser(user);

        return result.ifSuccess(user1 -> {
            log.info("User -> phone: {}, status: {}", user1.phone(), getMessage("users.update.success", locale));
            return user1;
        }).throwsEarlyIf(RuntimeException.class, error -> {
            log.info("User -> phone: {}, status: {}", user.phone(), getMessage("users.update.fail", locale));
            return new UserException("Error update user");
        }).execute();
    }

    private Result<User, RuntimeException> updateUser(User user) {
        return this.repository.update(user)
                .map(Result::<User, RuntimeException>successWithReturn)
                .orElse(Result.failWithProblem(new UserException("Error update user")));
    }
}
