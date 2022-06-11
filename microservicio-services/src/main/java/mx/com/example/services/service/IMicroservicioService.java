package mx.com.example.services.service;

import mx.com.example.commons.to.UserTO;
import java.util.List;

public interface IMicroservicioService {

    List<UserTO> getUsers();
}
