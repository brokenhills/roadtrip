package com.brokenhills.roadtrip.security;

import com.brokenhills.roadtrip.services.JwtRequestFilter;
import com.brokenhills.roadtrip.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.brokenhills.roadtrip.entities.UserRole.RoleType.ADMIN;

@Configuration
@EnableWebSecurity
public class MultiSecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    private final UserService userService;

    public MultiSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder());
    }

    @Order(1)
    @Configuration
    public static class RestApiSecurity extends WebSecurityConfigurerAdapter {

        private final JwtRequestFilter jwtRequestFilter;
        private final AuthEntryPoint authEntryPoint;

        public RestApiSecurity(JwtRequestFilter jwtRequestFilter, AuthEntryPoint authEntryPoint) {
            this.jwtRequestFilter = jwtRequestFilter;
            this.authEntryPoint = authEntryPoint;
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/api/**")
                    .csrf()
                    .disable()
                    .cors()
                    .disable()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .antMatchers("/api/auth/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(authEntryPoint)
                    .and()
                    .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }


    @Order(2)
    @Configuration
    public static class AdminApiSecurity extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/**")
                    .csrf()
                    .and()
                    .authorizeRequests()
                    .anyRequest()
                    .hasRole(ADMIN.name())
                    .and()
                    .formLogin()
                    .defaultSuccessUrl("/admin", true);
        }
    }
}
