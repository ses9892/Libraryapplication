package com.library.application.security;

import com.library.application.dto.Role;
import com.library.application.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@Slf4j
public class securityConfig extends WebSecurityConfigurerAdapter {
    private UserService userService;
    private Environment env;

    @Autowired
    public securityConfig(UserService userService, Environment env) {
        this.userService = userService;
        this.env = env;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/js/**","/pdf/**","/web/**","/build/**");
        //resource에 대한 인증은 무시 (접근허용)
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/test","/","/register","/duplication","/email","/forgetPwd").permitAll()
                .antMatchers("/library/**","/user-service/**","/chat/**","/chating/**").hasRole("USER")
                .antMatchers("/admin-service/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .successHandler(new LoginSuccessHandler(env))
                        .failureHandler(new LoginFailedHandler(env))
                        .permitAll()
                        .and()
                    .logout()
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID","remember-me")
                        .invalidateHttpSession(true)
                        .logoutSuccessHandler(new LogoutSuccessHandler() {
                            @Override
                            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                response.setStatus(HttpServletResponse.SC_OK);
                                response.sendRedirect("/");
                                log.info("로그아웃성공!");
                            }
                        });
//                .and()
//                .sessionManagement()
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//                .expiredUrl("/")
//                .sessionRegistry(sessionRegistry());
        http.rememberMe().tokenValiditySeconds(86400)
                            .rememberMeParameter("rememberMe")
                            .alwaysRemember(true)
                            .userDetailsService(userService);//초단위

    }
    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }


}
