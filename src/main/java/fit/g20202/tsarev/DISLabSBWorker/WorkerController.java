package fit.g20202.tsarev.DISLabSBWorker;

import fit.g20202.tsarev.DISLabSBWorker.DTO.ResponseToManagerDTO;
import fit.g20202.tsarev.DISLabSBWorker.DTO.TaskFromManagerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WorkerController {

    WorkerService service;

    @Autowired
    WorkerController(WorkerService service){
        this.service = service;
    }

    @PostMapping("/internal/api/worker/hash/crack/task")
    public ResponseToManagerDTO getTask(
            @RequestBody TaskFromManagerDTO task
    ) {

        service.startCrack(task);

        return new ResponseToManagerDTO();
    }

}
