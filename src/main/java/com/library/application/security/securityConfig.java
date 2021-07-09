package com.library.application.security;

import com.library.application.dto.Role;
import com.library.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
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
        http.authorizeRequests().antMatchers("/","/register","/user/**").permitAll()
                .antMatchers("/library/**").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/")
                    .loginProcessingUrl("/user/login")
                    .usernameParameter("userId")
                    .passwordParameter("password")
                    .successHandler(new LoginSuccessHandler(env))
                    .failureHandler(new LoginFailedHandler(env));

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

}
