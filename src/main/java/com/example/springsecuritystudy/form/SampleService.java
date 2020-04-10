package com.example.springsecuritystudy.form;

import com.example.springsecuritystudy.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SampleService {

    public void dashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Object credentials = authentication.getCredentials();
        boolean authenticated = authentication.isAuthenticated();


    }

    @Async
    public void asyncService() {
        SecurityLogger.log("Async Service");
        System.out.println("Async service is called.");
    }
}
