package com.backend.authservice;

import com.backend.authservice.eventProducer.UserInfoProducer;
import com.backend.authservice.repositories.UserRepository;
import com.backend.authservice.services.UserDetailsServiceimpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
  private final PasswordEncoder passwordEncoder;


   private final UserDetailsServiceimpl userDetailsServiceimpl;
   private final UserInfoProducer userInfoProducer;

    public SecurityConfig(PasswordEncoder passwordEncoder, UserDetailsServiceimpl userDetailsServiceimpl, UserInfoProducer userInfoProducer) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsServiceimpl = userDetailsServiceimpl;
        this.userInfoProducer = userInfoProducer;
    }
    public UserDetailsService userDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        return new UserDetailsServiceimpl(userRepository, userInfoProducer, passwordEncoder);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security,JwtAuthFilter jwtAuthFilter)throws Exception{
        return security
                .csrf(AbstractHttpConfigurer::disable).cors(CorsConfigurer::disable)
                .authorizeHttpRequests(auth ->auth
                        .requestMatchers("/app/**").authenticated()
                        .anyRequest().permitAll())
                .sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsServiceimpl);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();
    }
}
