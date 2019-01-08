package com.practice_day2.huyu.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice_day2.huyu.mapper.UserMapper;
import com.practice_day2.huyu.model.User;
import com.practice_day2.huyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        });
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/hello-mahasiswa/**").hasAnyRole("MAHASISWA")
                .antMatchers("/api/hello-dosen/**").hasAnyRole("DOSEN")
                .antMatchers(HttpMethod.GET,"/api/user/**").hasAnyRole("DOSEN","MAHASISWA")
                .antMatchers(HttpMethod.DELETE,"/api/user/**").hasAnyRole("DOSEN","MAHASISWA")
                .antMatchers(HttpMethod.PUT,"/api/user").hasAnyRole("DOSEN","MAHASISWA")
                .antMatchers(HttpMethod.POST, "/api/user").permitAll()
                .and()
                .formLogin()
                .loginPage("/api/login")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                        httpServletResponse.setContentType("application/json");
                        ObjectMapper mapper = new ObjectMapper();
                        User user = userService.readUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

                        String json = mapper.writeValueAsString(userMapper.userToUserRespone(user));
                        httpServletResponse.getWriter().print(json);
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                })
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                    }
                })
                .permitAll();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
