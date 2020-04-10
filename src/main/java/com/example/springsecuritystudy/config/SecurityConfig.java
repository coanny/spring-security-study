package com.example.springsecuritystudy.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    public AccessDecisionManager accessDecisionManager(){

        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");   //ROLE_ADMIN 은 ROLE_USER가 가지는 모든권한을 가진다.
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setRoleHierarchy(roleHierarchy);

        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(defaultWebSecurityExpressionHandler);
        List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(webExpressionVoter);
        AccessDecisionManager accessDecisionManager = new AffirmativeBased(voters);

        return accessDecisionManager;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .mvcMatchers("/", "/info", "/account/**", "/signup/**").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                .mvcMatchers("/user").hasRole("USER")
                .anyRequest().authenticated()
                .accessDecisionManager(accessDecisionManager());

        http.formLogin();
        http.httpBasic();

        http.logout()
                .logoutSuccessUrl("/");


        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
