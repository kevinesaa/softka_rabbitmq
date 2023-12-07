package EPA.Cuenta_Bancaria_Web.MQTest;

import com.rabbitmq.client.Delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.rabbitmq.Receiver;

@Service
public class TestMQConsumer {

    @Autowired
    private Receiver receiver;

    public Flux<Delivery> consumeNoAck(String queueName) {
        return receiver.consumeNoAck(queueName);
    }

    public void  closeReceiver() {
        receiver.close();
    }
}
