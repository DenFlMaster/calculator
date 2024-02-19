package com.flmaster.calculator;

import com.fasterxml.jackson.databind.Module;
import com.flmaster.calculator.repo.CategoryRepository;
import com.flmaster.calculator.rowmapper.CategoryRowMapper;
import com.flmaster.calculator.settings.AdminAuthSettings;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.FullyQualifiedAnnotationBeanNameGenerator;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@SpringBootApplication(
		nameGenerator = FullyQualifiedAnnotationBeanNameGenerator.class
)
@EnableConfigurationProperties({
		AdminAuthSettings.class
})
public class CalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorApplication.class, args);
	}

	@Bean(name = "com.flmaster.calculator.invoker.OpenApiGeneratorApplication.jsonNullableModule")
	public Module jsonNullableModule() {
		return new JsonNullableModule();
	}

}
