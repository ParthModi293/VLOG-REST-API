
package com.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.Security.CustomerUserDetailService;
import com.blog.Security.JwtAuthenticationEntryPoint;
import com.blog.Security.JwtAuthenticationFilter;

import jakarta.servlet.DispatcherType;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)



public class SecurityConfig {

	
	public static final String[] PUBLIC_URL= {
			
			"/api/auth/**",
			"/v2/api-docs",
			"/swagger/resources/**",
			"/swagger-ui/**",
			"/webjars/**"
			
			
	};
 	
	@Autowired
	private CustomerUserDetailService customerUserDetailService;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http

				.csrf().disable().authorizeHttpRequests()
				.requestMatchers(PUBLIC_URL).permitAll()
				.and()
			
				
				.authorizeHttpRequests().requestMatchers(HttpMethod.GET).permitAll().anyRequest()
				
				
				
		
				
				.authenticated().and()
				.exceptionHandling()
				.authenticationEntryPoint(this.jwtAuthenticationEntryPoint).and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).logout();

		// DefaultSecurityFilterChain build = http.build();
		return http.build();
	}

	

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(customerUserDetailService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationmanagerBean(AuthenticationConfiguration auth) throws Exception {

		return auth.getAuthenticationManager();

	}

}



/*
 * http.cors().and().csrf().disable()
 * .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).
 * and()
 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
 * and() .authorizeHttpRequests().requestMatchers("/api/auth/login").permitAll()
 * 
 * .anyRequest().authenticated();
 * 
 * 
 */

/*
 * @Bean public WebSecurityCustomizer webSecurityCustomizer() { return web ->
 * web.ignoring()
 * 
 * .requestMatchers("/api/auth/login"); }
 */
