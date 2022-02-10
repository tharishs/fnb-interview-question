package za.co.fnb.interview.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String API_PATH = "/api/**";
    private static final String ACTUATOR_PATH = "/actuator/**";

    @Value("${server.servlet.context-path}")
    String contextPath;


    @Bean
    GroupedOpenApi allApi() {

        return GroupedOpenApi.builder()
                .group("All")
                .pathsToMatch(API_PATH)
                .build();
    }


    @Bean
    GroupedOpenApi systemHealthApi() {

        return GroupedOpenApi.builder()
                .group("System Health")
                .pathsToMatch(ACTUATOR_PATH)
                .build();
    }


    @Bean
    public OpenAPI generalOpenApi() {

        return new OpenAPI()
                .addServersItem(new Server().url(contextPath))
                .info(getInfo())
                .components(new Components()
                        .addSecuritySchemes("basicScheme",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")));
    }

    @Bean
    Info getInfo() {
        return new Info()
                .title("Better Takealot Reviews")
                .description("Takealot Reviews specific service operations")
                .version("1.0")
                .contact(getContact());
    }

    @Bean
    Contact getContact() {
        return new Contact()
                .email("tharishsooruth@gmail.com")
                .name("Tharish Sooruth")
                .url("https://github.com/tharishs/takealot-better-reviews");

    }
}
