package com.vietphat.newswave.config;

import com.vietphat.newswave.enums.UserRole;
import com.vietphat.newswave.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(customUserDetailsService);

        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(config -> {
                    config.disable();
                })
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/quan-tri/**")
                                .hasAnyRole(UserRole.SUPER_ADMIN.toString())
                            .anyRequest().permitAll();
                })
                .formLogin(config -> {
                    config
                            .loginPage("/xac-thuc/dang-nhap")
                            .usernameParameter("j_username")
                            .passwordParameter("j_password")
                            .permitAll()
                            .loginProcessingUrl("/j_spring_security_check")
                            .successHandler(authenticationSuccessHandler())
                            .failureUrl("/xac-thuc/dang-nhap?incorrectCredentials=true");
                })
                .logout(config -> {
                    config
                            .logoutUrl("/xac-thuc/dang-xuat")
                            .logoutSuccessUrl("/xac-thuc/dang-nhap?logout=true")
                            .deleteCookies("JSESSIONID");
                })
                .exceptionHandling(config -> {
                    config
                            .accessDeniedPage("/xac-thuc/tu-choi-truy-cap");
                })
                .sessionManagement(config -> {
                    config
                            .maximumSessions(1)
                            .expiredUrl("/xac-thuc/dang-nhap?sessionTimeout=true");
                });

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

}
