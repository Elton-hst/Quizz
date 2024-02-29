package br.com.mjv.quizz.application.useCases.user;

import br.com.mjv.quizz.domain.config.result.Result;
import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.domain.user.dto.GetUserDto;
import br.com.mjv.quizz.domain.user.repository.UsersRepository;
import br.com.mjv.quizz.infrastructure.configs.exception.UserException;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

@Slf4j
public class FindUserUseCase {

    private final UsersRepository repository;
    private final ResourceBundle resourceBundle;

    public FindUserUseCase(UsersRepository repository, ResourceBundle resourceBundle) {
        this.repository = repository;
        this.resourceBundle = resourceBundle;
    }

    public String getMessage(String key, Locale locale) {
        return ResourceBundle.getBundle(resourceBundle.getBaseBundleName(), locale).getString(key);
    }

    public User execute(GetUserDto userDto) {
        User user = new User(userDto.phone());
        return result(user, Locale.ENGLISH);
    }

    public User execute(GetUserDto userDto, Locale locale) {
        User user = new User(userDto.phone());
        return result(user, locale);
    }

    private User result(User user, Locale locale) {
        var result = findUser(user);

        return result.ifSuccess(user1 -> {
            log.info("User -> phone: {}, status: {}", user1.phone(), getMessage("users.find.success", locale));
            return user1;
        }).throwsEarlyIf(RuntimeException.class, error -> {
            log.info("User -> phone: {}, status: {}", user.phone(), getMessage("users.find.fail", locale));
            return new UserException(error.getLocalizedMessage());
        }).execute();
    }

    private Result<User, RuntimeException> findUser(User user) {
        Optional<User> newUser = repository.findByPhone(user.phone());
        return newUser.map(Result::<User, RuntimeException>successWithReturn)
                .orElse(Result.failWithProblem(new UserException("User not found")));
    }

}
