package uz.ilmnajot.school.security.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "Bearer",
        description = "Bearer token description here",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Education");

        Contact myContact = new Contact();
        myContact.setName("Elbek Umar");
        myContact.setEmail("user@gmail.com");

        Info information = new Info()
                .title("Education System API")
                .version("1.0")
                .description("This API exposes endpoints to manage Education System.")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}