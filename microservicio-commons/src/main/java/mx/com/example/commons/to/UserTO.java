package mx.com.example.commons.to;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class UserTO implements Serializable {

    private int id;
    private String name;
    private String lastName;
    private int age;
}
