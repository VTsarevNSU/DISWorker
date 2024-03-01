package fit.g20202.tsarev.DISLabSBWorker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
//@Import(RabbitConfiguration.class)
public class DisLabSbWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisLabSbWorkerApplication.class, args);
	}

}
