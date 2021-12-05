package com.toy.javaserver.api;

import com.toy.javaserver.api.common.annotation.EnableApiResponse;
import com.toy.javaserver.api.configuration.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableApiResponse // Response시에 Response Model을 자동으로 붙여주는 역활
@SpringBootApplication(
		scanBasePackages = {"com.toy.javaserver.api"},
		exclude = {DataSourceTransactionManagerAutoConfiguration.class, DataSourceAutoConfiguration.class})
@ImportAutoConfiguration(value = {
		SwaggerConfiguration.class
})
@EnableJpaRepositories(value = "com.toy.javaserver.api.domain")
@EnableAspectJAutoProxy
@EntityScan(basePackageClasses = Jsr310Converters.class)
@EnableConfigurationProperties
public class JavaServerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) { return super.configure(builder); }

	public static void main(String[] args) {
		SpringApplication.run(JavaServerApplication.class, args);
	}
}
