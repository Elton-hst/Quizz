package br.com.mjv.quizz.infrastructure.web;

import br.com.mjv.quizz.application.facede.UserFacade;
import br.com.mjv.quizz.domain.user.User;
import br.com.mjv.quizz.domain.user.dto.CreateUpdateUserDto;
import br.com.mjv.quizz.domain.user.dto.GetUserDto;
import br.com.mjv.quizz.infrastructure.web.docs.UserControllerDocs;
import br.com.mjv.quizz.domain.config.result.ReturnWithMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping({"/user"})
@RequiredArgsConstructor
public class UserController implements UserControllerDocs {

    private final UserFacade userFacade;

    @PostMapping
    @Transactional
    public ResponseEntity<User> createUser(@RequestBody CreateUpdateUserDto userDto, UriComponentsBuilder uriBuilder, final Locale locale) throws Exception {
        var user = userFacade.createUser(userDto, locale);
        URI uri = uriBuilder.path("/user/{name}").buildAndExpand(userDto.name()).toUri();
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping({"/{phone}"})
    public ResponseEntity<?> findUser(@PathVariable String phone) {
        GetUserDto userDto = new GetUserDto(phone);
        var user = userFacade.findUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody CreateUpdateUserDto userDto, final Locale locale) throws Exception {
        var user = userFacade.updateUser(userDto, locale);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
