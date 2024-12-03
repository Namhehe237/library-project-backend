package backend.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.accept.ContentNegotiationStrategy;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import com.okta.spring.boot.oauth.Okta;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSec) throws Exception {

        // Disable cross-site request forgery
        httpSec.csrf(csrf -> csrf.disable());

        // Add CORS filters
        httpSec.cors(cors -> {});

        // Protect endpoints at api/<types>/secure
        httpSec.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/books/secure/**").authenticated()
                .requestMatchers("/api/books/**", "/api/reviews/**").permitAll()
        );

        // Configure OAuth2 Resource Server with JWT
        httpSec.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
        );

        // Add content negotiation strategy
        httpSec.setSharedObject(ContentNegotiationStrategy.class, new HeaderContentNegotiationStrategy());

        // Force a non-empty response body for 401s to make the response friendly
        Okta.configureResourceServer401ResponseBody(httpSec);

        return httpSec.build();
    }

    // Customize JWT Authentication Converter if needed
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        // Customize to map roles/authorities if necessary
        return converter;
    }
}
