package com.car.management.carmanagementapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.car.management.carmanagementapp.service.EventService;

@SpringBootApplication
public class CarManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarManagementAppApplication.class, args);
	}
	
	@Autowired
	private EventService eventService;
	
	@Scheduled(fixedRate = 60000L)
	void checkForEvent() {
		eventService.checkForVehicleEventAndSendEmailToUser(eventService.getAllUsers());
	}

}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {
	
}
