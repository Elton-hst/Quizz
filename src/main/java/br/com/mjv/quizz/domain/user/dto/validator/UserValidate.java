package br.com.mjv.quizz.domain.user.dto.validator;

import br.com.mjv.quizz.domain.config.validator.Validator;
import br.com.mjv.quizz.domain.user.dto.CreateUpdateUserDto;

import java.util.HashMap;
import java.util.Map;

public class UserValidate implements Validator<CreateUpdateUserDto> {

    @Override
    public Map<String, String> validate(CreateUpdateUserDto userDto) {
        var errors = new HashMap<String,String>();

        if (userDto.phone().isEmpty()) {
            errors.put("phone is", "null");
            return errors;
        }

        if (userDto.name().isEmpty()) {
            errors.put("email is", "null");
            return errors;
        }

        return errors;
    }

}
