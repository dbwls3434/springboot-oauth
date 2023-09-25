package org.yujin.myproc.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.yujin.myproc.entity.User;
import org.yujin.myproc.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User principal = userRepository.findByEmail(email);
        return new PrincipalDetail(principal);
    }
}