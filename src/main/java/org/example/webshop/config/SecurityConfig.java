package org.example.webshop.config;

import org.example.webshop.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/", "/index/**", "/register", "/register/new", "/register/add", "/login/**", "/error/**").permitAll()
                        .requestMatchers("/admins/**").hasAuthority("ADMIN")
                        .requestMatchers("/loginAdmin/**").hasAuthority("ADMIN")
                        .requestMatchers("/registerRedactor/**").hasAuthority("ADMIN")
                        .requestMatchers("/redactor/**").hasAuthority("ADMIN")
                        .requestMatchers("/catalogs/list/**").hasAuthority("REDACTOR")
                        .requestMatchers("/products/**").hasAuthority("REDACTOR")
                        .requestMatchers("/catalogs/create/**").hasAuthority("REDACTOR")
                        .requestMatchers("/catalogs/editCatalog/**").hasAuthority("REDACTOR")
                        .requestMatchers("/catalogs/delete/**").hasAuthority("REDACTOR")
                        .requestMatchers("/customer/**").hasAuthority("CUSTOMER")
                        .requestMatchers("/catalogs/listCustomer/**").hasAuthority("CUSTOMER")
                        .requestMatchers("/catalogs/catalog/**").hasAuthority("CUSTOMER")
                        .requestMatchers("/carts/**").hasAuthority("CUSTOMER")
                        .requestMatchers("/orders/**").hasAuthority("CUSTOMER")
                        .requestMatchers("/pay/**").hasAuthority("CUSTOMER")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login/form")
                        .loginProcessingUrl("/login/enter")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureUrl("/login/form?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login/form?logout=true")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/favicon.ico");
    }
}
