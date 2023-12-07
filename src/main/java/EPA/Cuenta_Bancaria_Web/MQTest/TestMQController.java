package EPA.Cuenta_Bancaria_Web.MQTest;

import EPA.Cuenta_Bancaria_Web.MQTest.model.RequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestMQController {

    @Autowired
    private TestMQSenderService service;

    @PostMapping("/rabbit/{queueName}")
    public  Mono<Void> publishMessage(@PathVariable String queueName,@RequestBody RequestDTO request) {
        return service.sendMessage(queueName,request.getMessage());
    }
}
