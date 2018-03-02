package de.idealo.toyrobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author uysharma
 */
@SpringBootApplication(scanBasePackages={"de.idealo.toyrobot"})
public class SpringBootToyRobotSimulator {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootToyRobotSimulator.class, args);
	}
}
