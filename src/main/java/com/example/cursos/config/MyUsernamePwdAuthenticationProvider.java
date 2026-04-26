package com.example.cursos.config;

import com.example.cursos.model.User;
import com.example.cursos.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MyUsernamePwdAuthenticationProvider(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findUserWithRoleByName(username).orElse(null);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // --- O CÓDIGO ENTRA AQUI ---
            List<GrantedAuthority> authorities = new ArrayList<>();

            // Adicionamos o prefixo ROLE_ para que o .hasRole("ADMIN") funcione corretamente
            // Se no banco estiver "ADMIN", aqui ele vira "ROLE_ADMIN"
            authorities.add(new SimpleGrantedAuthority(user.getRole().getRole().toUpperCase()));

            return new UsernamePasswordAuthenticationToken(username, null, authorities);
            // ---------------------------
        } else {
            throw new AuthenticationCredentialsNotFoundException("Credenciais inválidas!");
        }
    }

    @Override
    public boolean supports(Class<?> authenticationType) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authenticationType);
    }
}