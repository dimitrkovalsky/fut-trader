package com.liberty.config;

import com.liberty.common.StringConstants;
import com.liberty.security.AuthFilter;
import com.liberty.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Dmytro_Kovalskyi.
 * @since 13.01.2017.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthFilter authFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("swagger").password("swagger")
                .authorities(new Role(StringConstants.ROLE_SWAGGER_USER));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests()
                .and()
                .authorizeRequests().antMatchers("/swagger/**")
                .authenticated()
                //Secured requests
                .and()
                .authorizeRequests().antMatchers("/secure/**").hasRole(StringConstants.ROLE_USER)
                .and()
                .httpBasic()
        ;
        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
    }

}