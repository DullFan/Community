package com.ruoyi.framework.web.service;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Data
public class EmailCodeAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public EmailCodeAuthenticationProvider(UserDetailsService userDetailsService){
        setUserDetailsService(userDetailsService);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        EmailCodeAuthenticationToken authenticationToken = (EmailCodeAuthenticationToken) authentication;
        String email = (String) authentication.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        EmailCodeAuthenticationToken authenticationTokenRes = new EmailCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationTokenRes.setDetails(authenticationToken.getDetails());
        return authenticationTokenRes;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
