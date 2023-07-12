package com.exnotis.backend.security.service;

import com.exnotis.backend.user.repository.UserRepository;
import com.exnotis.backend.security.model.AppUserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;
    public UserDetailsServiceImpl(UserRepository repository){
        this.repository=repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserPrincipal principal = new AppUserPrincipal(repository.findAppUserByUsername(username));
        return principal;
    }
}
