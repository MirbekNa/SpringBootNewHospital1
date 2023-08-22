package peaksoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootNewHospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNewHospitalApplication.class, args);

        System.out.println("Spring Boot приложение успешно запущено!");
    }

}
