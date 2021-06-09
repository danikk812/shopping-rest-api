package by.danilap.codextask.config;

import by.danilap.codextask.security.UserDetailsServiceImpl;
import by.danilap.codextask.security.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        private final JwtFilter jwtFilter;
        private final AuthenticationEntryPoint unauthorizedHandler;
        private final PasswordEncoder passwordEncoder;
        private final UserDetailsServiceImpl userDetailsService;

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
                return super.authenticationManagerBean();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http
                        .httpBasic().disable()
                        .csrf().disable()
                        .cors().disable()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        .and()
                        .exceptionHandling()
                        .authenticationEntryPoint(unauthorizedHandler)
                        .and()
                        .authorizeRequests()
                        .antMatchers().permitAll()
                        .anyRequest().permitAll();

                http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        }
}
