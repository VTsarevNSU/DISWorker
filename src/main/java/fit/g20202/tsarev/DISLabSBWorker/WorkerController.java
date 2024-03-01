package fit.g20202.tsarev.DISLabSBWorker;

import fit.g20202.tsarev.DISLabSBWorker.DTO.ResponseToManagerDTO;
import fit.g20202.tsarev.DISLabSBWorker.DTO.TaskForWorkerDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

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
    public void getTask(
            TaskForWorkerDTO task
    ) {
        service.startCrack(task);
    }

}
