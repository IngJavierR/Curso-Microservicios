package mx.com.example.services.facade.impl;

import mx.com.example.commons.to.UserTO;
import mx.com.example.services.facade.IMicroservicioFacade;
import mx.com.example.services.service.IMicroservicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MicroservicioFacade implements IMicroservicioFacade {

    @Autowired
    private IMicroservicioService microservicioService;

    public List<UserTO> getAllUsers() {
        return this.microservicioService.getUsers();
    }
}
