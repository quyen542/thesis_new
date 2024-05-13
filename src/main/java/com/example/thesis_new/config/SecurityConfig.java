package com.example.thesis_new.config;

import com.example.thesis_new.service.CustomLogoutSuccessHandler;
import com.example.thesis_new.service.CustomSuccessHandler;
import com.example.thesis_new.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf(c -> c.disable())

                .authorizeHttpRequests(request -> request.requestMatchers("/admin/**").hasAuthority("admin")
                        .requestMatchers("/delivery/**").hasAuthority("delivery")
                        .requestMatchers("/customer/**").hasAuthority("customer")
                        .requestMatchers("/home", "/signup", "/update-category", "/update-rating", "/search", "/css/**", "/js/**" , "/image/**", "/verify", "/forgotPassword", "/resetPassword").permitAll()
                        .anyRequest().authenticated())

                .formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login")
                        .successHandler(customSuccessHandler) .failureUrl("/login?error=false").permitAll())

                .logout(form -> form.invalidateHttpSession(true).clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessHandler(customLogoutSuccessHandler).permitAll());

        return http.build();

    }

    @Autowired
    public void configure (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
}
