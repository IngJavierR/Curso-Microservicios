package mx.com.example.services.stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    static final Logger LOG = LogManager.getLogger(KafkaListeners.class);

    //@KafkaListener(topics = "prueba", groupId = "order")
    public void listenGroupFoo(String message) {
        LOG.info("Received Message in group 'foo': " + message);
    }
}
