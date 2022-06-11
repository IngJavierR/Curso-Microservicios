package mx.com.example.persistence;

import mx.com.example.model.UserDO;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UserDAO extends CrudRepository<UserDO, Long> {

    List<UserDO> findByLastName(String lastName);
}
