package br.com.mjv.quizz.domain.whatsApp.webhook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Component {

    private String type;
    private List<Parameter> parameters = new ArrayList<Parameter>();
    private String sub_type;
    private String index;

}