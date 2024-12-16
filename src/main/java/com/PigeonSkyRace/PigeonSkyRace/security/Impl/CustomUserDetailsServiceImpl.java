package com.PigeonSkyRace.PigeonSkyRace.security.Impl;

import com.PigeonSkyRace.PigeonSkyRace.repository.UserRepository;
import com.PigeonSkyRace.PigeonSkyRace.security.CustomUserDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.PigeonSkyRace.PigeonSkyRace.model.User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
