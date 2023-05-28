package com.example.wasteControl;

import com.example.wasteControl.config.SpringAuditor;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@OpenAPIDefinition(info =@Info(title = "Waste Control Swagger APIs"))
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class WasteControlApplication {
	@Bean
	public AuditorAware<String> auditorAware() {

		return new SpringAuditor();
	}
	@Bean
	public ModelMapper modelMapper() {

		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(WasteControlApplication.class, args);
	}

}
