package br.com.mjv.quizz.application.useCases.user;

import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.domain.user.dto.GetUserDto;
import br.com.mjv.quizz.domain.user.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class FindUserUseCase {

    private final UsersRepository repository;

    public FindUserUseCase(UsersRepository repository) {
        this.repository = repository;
    }

    public User execute(GetUserDto userDto) {
        User user = new User(userDto.phone());
        return findUser(user).orElse(null);
    }

    private Optional<User> findUser(User user) {
        return repository.findByPhone(user.phone());
    }

}
