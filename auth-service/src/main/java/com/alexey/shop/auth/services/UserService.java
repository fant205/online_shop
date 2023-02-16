package com.alexey.shop.auth.services;

import com.alexey.shop.auth.model.Role;
import com.alexey.shop.auth.model.User;
import com.alexey.shop.auth.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final RoleService roleService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userOptional = usersRepository.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException(username));
        List<GrantedAuthority> authorities = user.getRoles().stream().map(Role::getName).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public Optional<User> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public void createUser(User user) {
        user.setRoles(List.of(roleService.getUserRole()));
        usersRepository.save(user);
    }
}
