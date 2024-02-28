package br.com.mjv.quizz.infrastructure.configs;

import br.com.mjv.quizz.application.facede.QuizFacade;
import br.com.mjv.quizz.application.facede.UserFacade;
import br.com.mjv.quizz.application.useCases.alternative.CreateAlternativeUseCase;
import br.com.mjv.quizz.application.useCases.question.CreateQuestionUseCase;
import br.com.mjv.quizz.application.useCases.quiz.CreateQuizUseCase;
import br.com.mjv.quizz.application.useCases.quiz.FindQuizUseCase;
import br.com.mjv.quizz.application.useCases.quiz.QuizManagementUseCase;
import br.com.mjv.quizz.application.useCases.user.CreateUserUseCase;
import br.com.mjv.quizz.application.useCases.user.FindUserUseCase;
import br.com.mjv.quizz.application.useCases.user.UpdateUserUseCase;
import br.com.mjv.quizz.application.useCases.user.UserManagementUseCase;
import br.com.mjv.quizz.application.useCases.whatsApp.ApiWhatsAppService;
import br.com.mjv.quizz.application.useCases.whatsApp.ContextChatUseCase;
import br.com.mjv.quizz.application.useCases.whatsApp.MessageManagementUseCase;
import br.com.mjv.quizz.application.useCases.whatsApp.ReadMessageUseCase;
import br.com.mjv.quizz.domain.quiz.repository.QuizRepository;
import br.com.mjv.quizz.domain.user.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.ResourceBundle;

@Configuration
public class BeansClass {
    
    @Bean
    public CreateUserUseCase createUserUseCase(UsersRepository repository, ResourceBundle messageBundle) {
        return new CreateUserUseCase(repository, messageBundle);
    }
    
    @Bean
    public FindUserUseCase findUserUseCase(UsersRepository repository) {
        return new FindUserUseCase(repository);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(UsersRepository repository, ResourceBundle messageBundle) {
        return new UpdateUserUseCase(repository, messageBundle);
    }

    @Bean
    public UserManagementUseCase userManagementUseCase(UpdateUserUseCase updateUserUseCase) {
        return new UserManagementUseCase(updateUserUseCase);
    }
    
    @Bean
    public CreateQuizUseCase createQuizUseCase(QuizRepository quizRepository, CreateQuestionUseCase createQuestionUseCase) {
        return new CreateQuizUseCase(quizRepository, createQuestionUseCase);
    }

    @Bean
    public FindQuizUseCase findQuizUseCase(QuizRepository repository) {
        return new FindQuizUseCase(repository);
    }

    @Bean
    public QuizManagementUseCase quizManagementUseCase(QuizFacade quizFacade, UserManagementUseCase userManagementUseCase) {
        return new QuizManagementUseCase(quizFacade, userManagementUseCase);
    }

    @Bean
    public CreateQuestionUseCase createQuestionUseCase(CreateAlternativeUseCase createAlternativeUseCase) {
        return new CreateQuestionUseCase(createAlternativeUseCase);
    }

    @Bean
    public CreateAlternativeUseCase createAlternativeUseCase() {
        return new CreateAlternativeUseCase();
    }

    @Bean
    public ReadMessageUseCase readMessageUseCase(ContextChatUseCase contextChat, UserFacade userService) {
        return new ReadMessageUseCase(contextChat, userService);
    }

    @Bean
    public ContextChatUseCase ContextChatUseCase(UserManagementUseCase userManagementUseCase, QuizManagementUseCase quizManagementUseCase, MessageManagementUseCase messageManagementUseCase) {
        return new ContextChatUseCase(userManagementUseCase, quizManagementUseCase, messageManagementUseCase);
    }

    @Bean
    public MessageManagementUseCase messageManagementUseCase(ApiWhatsAppService apiWhatsAppService) {
        return new MessageManagementUseCase(apiWhatsAppService);
    }

    @Bean
    public ApiWhatsAppService apiWhatsAppService(@Value("${whatsapp.identification}") String identification, @Value("${whatsapp.token}") String token) {
        return new ApiWhatsAppService(identification, token);
    }
}
