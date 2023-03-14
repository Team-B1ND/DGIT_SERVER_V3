package dodam.b1nd.dgit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class DgitApplication {

	public static void main(String[] args) {
		SpringApplication.run(DgitApplication.class, args);
	}

}
