package com.gitbaby.club.config;


import com.gitbaby.club.handler.ClubLoginSuccessHandler;
import com.gitbaby.club.service.ClubUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {
  @Autowired
  private ClubUserDetailsService userDetailsService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

//  @Bean
//  public InMemoryUserDetailsManager inDetailsService() {
//    UserDetails user = User.builder()
//            .username("user1")
//            .password(passwordEncoder().encode("1111"))
//            .roles("USER")
//            .build();
//
//    return new InMemoryUserDetailsManager(user);
//  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(auth -> {
              auth.requestMatchers("sample/all", "error").permitAll()
                      .requestMatchers("member/modify").hasRole("USER")
                      .requestMatchers("sample/admin").hasRole("ADMIN")
                      .anyRequest().authenticated();
            })
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(form ->
                    form.defaultSuccessUrl("/sample/all", false))
                        .logout(Customizer.withDefaults())
            .oauth2Login(form -> form.defaultSuccessUrl("/sample/all", false)
                    .successHandler(successHandler()))
            .rememberMe(auth -> auth.tokenValiditySeconds(60*60*7)
                    .userDetailsService(userDetailsService));
    return http.build();
  }

  @Bean
  public ClubLoginSuccessHandler successHandler() {
    return new ClubLoginSuccessHandler(passwordEncoder());
  }
}

