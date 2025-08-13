package entelect.training.incubator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    InMemoryUserDetailsManager inMemoryAuthManager() {
        return new InMemoryUserDetailsManager(
                User.builder().username("user").password("userpassword").roles("USER").build(),
                User.builder().username("admin").password("adminpassword").roles("ADMIN").build()
        );
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // !!! Disclaimer: NEVER DISABLE CSRF IN PRODUCTION !!!
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/bookings/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/bookings").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        return http.build();
    }
}