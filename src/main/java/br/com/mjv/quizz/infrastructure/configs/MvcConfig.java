package br.com.mjv.quizz.infrastructure.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import java.util.ResourceBundle;

@Configuration
public class MvcConfig {

    @Bean
    public LocaleResolver sessionLocaleResolver() {
        return new AcceptHeaderResolver();
    }

    @Bean
    public ResourceBundle messageBundle() {
        return ResourceBundle.getBundle("ValidationMessages");
    }

}
