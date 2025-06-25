// infrastructure/config/SecurityConfig.java
package org.example.commerce.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.example.commerce.infrastructure.adapter.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)   // ← habilita @PreAuthorize
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;


    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        var entry = new BasicAuthenticationEntryPoint();
        entry.setRealmName("commerce-api");
        return entry;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    @Bean
    public SecurityFilterChain chain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPoint()) // 401
                .accessDeniedHandler(accessDeniedHandler())         // 403
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/reportes/**")
                .hasRole("ADMINISTRADOR")
                .antMatchers(HttpMethod.DELETE,"/api/comerciantes/**")
                .hasRole("ADMINISTRADOR")
                .anyRequest().authenticated()
                .and()

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authMgr(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
