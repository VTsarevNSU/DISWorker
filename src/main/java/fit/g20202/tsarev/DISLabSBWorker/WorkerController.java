package fit.g20202.tsarev.DISLabSBWorker;

import fit.g20202.tsarev.DISLabSBWorker.DTO.ResponseToManagerDTO;
import fit.g20202.tsarev.DISLabSBWorker.DTO.TaskForWorkerDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.nio.channels.Channel;

@Component
@EnableRabbit
public class WorkerController {

    @Autowired
    WorkerService service;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    Queue workerQueue;

    @RabbitListener(queues = "worker_queue")
    public void receive(
            TaskForWorkerDTO task
    ) {
        //System.out.println(channel);
        //System.out.println(tag);
        service.startCrack(task);
    }

}
