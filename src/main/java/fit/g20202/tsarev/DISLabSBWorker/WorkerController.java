package fit.g20202.tsarev.DISLabSBWorker;

import com.rabbitmq.client.Channel;
import fit.g20202.tsarev.DISLabSBWorker.DTO.TaskForWorkerDTO;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@EnableRabbit
public class WorkerController {

    @Autowired
    WorkerService service;

    @RabbitListener(queues = "worker_queue")
    public void receive(
            TaskForWorkerDTO task,
            Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long tag
    ) throws IOException {
        service.startCrack(task, channel, tag);
    }

}
