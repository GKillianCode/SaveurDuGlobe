package com.killiangodet.recette.config;

import com.killiangodet.recette.role.repository.RoleRepository;
import com.killiangodet.recette.user.model.User;
import com.killiangodet.recette.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Re définition du [UserDetailsService] pour le mécanisme d'authentification
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> account = Optional.ofNullable(userRepository.findByUsername(username));
        if (account.isPresent()) {
            return account.get();
        }

        throw new UsernameNotFoundException("the username $username doesn't exist");
    }
}
