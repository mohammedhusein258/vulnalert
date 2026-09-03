package dev.vulnalert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VulnAlertApplication {
    public static void main(String[] args) { SpringApplication.run(VulnAlertApplication.class, args); }
}

