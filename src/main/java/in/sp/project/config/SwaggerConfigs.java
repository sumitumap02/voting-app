package in.sp.project.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfigs {

	@Bean
	  public OpenAPI springShopOpenAPI() {
		
		String schemeName ="bearerscheme";
		
	      return new OpenAPI()
	    		  .addSecurityItem(new SecurityRequirement()
	    		  .addList(schemeName)
	    				  )
	    		  .components(new Components()
	    		   .addSecuritySchemes(schemeName, new SecurityScheme().name(schemeName)
	    				   .type(SecurityScheme.Type.HTTP)
	    				   .bearerFormat("jwt")
	    				   .scheme("bearer")
	    				   )
	    		   )
	              .info(new Info().title("Online-Voting-System")
	              .description("Spring Blog sample application")
	              .version("1.0")
	              .contact(new Contact().email("sumit@gmail.com").name("sumit"))
	              .license(new License().name("Apache 2.0").url("http://springdoc.org")))
	              .externalDocs(new ExternalDocumentation()
	              .description("SpringShop Wiki Documentation")
	              .url("https://springshop.wiki.github.org/docs"));
	  }
    
}



















