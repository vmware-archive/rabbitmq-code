package spring.rabbitmq.summit.springamqpresilientstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

@SpringBootApplication
@EnableScheduling
@Controller
public class SpringAmqpResilientStartApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAmqpResilientStartApplication.class, args);
	}

}
