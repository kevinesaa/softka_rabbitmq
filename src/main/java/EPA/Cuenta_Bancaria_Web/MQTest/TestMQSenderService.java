package EPA.Cuenta_Bancaria_Web.MQTest;


import com.rabbitmq.client.AMQP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Sender;

@Service
public class TestMQSenderService {


    @Autowired
    private Sender sender;

    private static final String QUEUE = "demo-queue";

    public Mono<AMQP.Queue.DeclareOk> declareQueue() {
        return sender.declareQueue(new QueueSpecification().name(QUEUE));
    }

    // Enviar un mensaje a la cola con el contenido dado
    public Mono<Void> sendMessage(String queueName, String content) {

        return sender.send(

                Mono.just(new OutboundMessage("", queueName, content.getBytes()))
        );
    }


    // Cerrar el sender al terminar
    public void closeSender() {

        sender.close();
    }


}
