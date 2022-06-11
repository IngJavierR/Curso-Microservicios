package mx.com.example.services.facade;

import mx.com.example.commons.to.UserTO;
import mx.com.example.services.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class MicroservicioFacadeTest extends BaseTest {

    @Test
    public void ShouldReturnAllUsers() {

        List<UserTO> users = this.microservicioFacade.getAllUsers();

        Assert.assertEquals(1, users.size());
    }
}