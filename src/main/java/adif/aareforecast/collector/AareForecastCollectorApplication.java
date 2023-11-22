package adif.aareforecast.collector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AareForecastCollectorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AareForecastCollectorApplication.class, args);
	}
}
