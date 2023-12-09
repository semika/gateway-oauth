package iit.ase.cw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayOauthApplication.class, args);
	}
	
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("customer", r -> r.path("/customer/**")
					.filters(f -> f.addRequestHeader("Module", "Customer")) // Prevents cookie being sent downstream
					.uri("http://localhost:9006/")) // Taking advantage of docker naming

			.route("product", r -> r.path("/product/**")
				.filters(f ->  f.addRequestHeader("Module", "Product")) // Prevents cookie being sent downstream
				.uri("http://localhost:9007/")) // Taking advantage of docker naming

			.route("user", r -> r.path("/user/**")
				.filters(f ->  f.addRequestHeader("Module", "User")) // Prevents cookie being sent downstream
				.uri("http://localhost:9005/")) // Taking advantage of docker naming

				.build();
	}

}
