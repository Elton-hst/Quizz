package br.com.mjv.quizz;

import br.com.mjv.quizz.application.useCases.user.CreateUserUseCase;
import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.domain.user.dto.CreateUpdateUserDto;
import br.com.mjv.quizz.domain.user.repository.UsersRepository;
import br.com.mjv.quizz.infrastructure.persistence.entity.basicEntity.ChatContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class CreateUserUseCaseTest {

    private CreateUserUseCase createUserUseCase;
    private CreateUpdateUserDto userDto;
    private User user;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private ResourceBundle resourceBundle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        createUserUseCase = new CreateUserUseCase(usersRepository, resourceBundle);
        this.userDto = new CreateUpdateUserDto("Elton", "5581986638021");

        this.user = new User("Elton", "5581986638021", 0.0, ChatContext.INITIALIZED_CHAT);
    }

    @Test
    void correctTest() throws Exception {
        //when(resourceBundle.getBaseBundleName()).thenReturn("messages");
        //when(resourceBundle.getString("users.create.success")).thenReturn("User created successfully");

        //var result = new ReturnWithMessage<User>("User created successfully", user);

        //when(usersRepository.add(user)).thenReturn();

        var actualResult = createUserUseCase.execute(userDto, Locale.ENGLISH);

        assertEquals(actualResult.getData(), user);
    }
}
