package com.System.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.System.Service.UserDetailsServiceImpl;
import com.System.Service.UserImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private final UserDetailsServiceImpl userDetailsServiceImp;

	private final JwtAuthenticationFilter jwtauthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(req -> 
				req.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll().
				requestMatchers("/user/login/**", "/user/register/**",
						"/user/password/**").permitAll()
						.requestMatchers("/student/**",
								"/user/**","/courses")
						.hasAuthority("STUDENT").
						requestMatchers("/addCourse","/addCourse","/admin/**","/deleteCourse/{title}","/courses").hasAnyAuthority("ADMIN"))
				.userDetailsService(userDetailsServiceImp)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtauthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();

	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImp, JwtAuthenticationFilter authenticationFilter) {
		this.userDetailsServiceImp = userDetailsServiceImp;
		this.jwtauthenticationFilter = authenticationFilter;
	}


}
