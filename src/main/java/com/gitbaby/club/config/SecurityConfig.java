package com.gitbaby.club.config;


import com.gitbaby.club.security.filter.ApiCheckFilter;
import com.gitbaby.club.security.filter.ApiLoginFilter;
import com.gitbaby.club.security.handler.ClubLoginSuccessHandler;
import com.gitbaby.club.security.service.ClubUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Log4j2
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
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
  public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
    ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login");
    apiLoginFilter.setAuthenticationManager(authenticationManager);

    http
            .authorizeHttpRequests(auth -> {
              auth.requestMatchers("sample/all", "error", "note/**", "swagger-ui/**", "api/login/**").permitAll()
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
            .rememberMe(auth -> auth.tokenValiditySeconds(60*2)
                    .userDetailsService(userDetailsService))
            .addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public ClubLoginSuccessHandler successHandler() {
    return new ClubLoginSuccessHandler(passwordEncoder());
  }

  @Bean
  public ApiCheckFilter apiCheckFilter() {
    // note/한글자라도 있어야 함.
    return new ApiCheckFilter("/note/**/*");
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }
}

