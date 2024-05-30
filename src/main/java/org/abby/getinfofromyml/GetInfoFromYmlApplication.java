package org.abby.getinfofromyml;

import org.abby.getinfofromyml.initproperties.OrganizationLevelData;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GetInfoFromYmlApplication {


	public static void main(String[] args) {
        SpringApplication.run(GetInfoFromYmlApplication.class, args);
    }

	@Bean
	public CommandLineRunner run(ApplicationContext appContext) {
		return args -> {
			// 獲取 Spring 管理的 OrganizationLevelData bean
			OrganizationLevelData organizationLevelData = appContext.getBean(OrganizationLevelData.class);
			// 調用方法
			organizationLevelData.soutOrganizationLevels(organizationLevelData);
		};
	}
}
