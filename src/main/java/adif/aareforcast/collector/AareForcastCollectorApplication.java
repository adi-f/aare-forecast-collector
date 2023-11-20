package adif.aareforcast.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AareForcastCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AareForcastCollectorApplication.class, args);
	}
}
