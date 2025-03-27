package cookcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import cookcloud.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {
	
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http.csrf().disable();
    	
    	http
        .authorizeHttpRequests(auth -> auth
	        .requestMatchers("/css/**", "/js/**", "/", "/img/**", "/signup/**", "/login", "/recipes", "/recipes/recipe/**", "/recipes/search/**").permitAll()
            .anyRequest().authenticated()
        	)
        	.formLogin(form -> form
	        .loginPage("/login")
	        .loginProcessingUrl("/login") // Spring Security가 처리할 로그인 POST URL
	        .defaultSuccessUrl("/mypage", true)
	        .permitAll()
	        )
        	.logout(logout -> logout
                    .logoutSuccessUrl("/")
             );

        return http.build();
    }

}
