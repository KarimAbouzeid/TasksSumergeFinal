package org.example.config;


import org.example.filter.CustomFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/course/viewCourse/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/course/addCourse/").hasRole("ADMIN")
                        .requestMatchers("/course/updateCourse/**").hasRole("ADMIN")
                        .requestMatchers("/course/updateCourseJDBC/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/course/deleteCourse/**").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .addFilterBefore(new CustomFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
