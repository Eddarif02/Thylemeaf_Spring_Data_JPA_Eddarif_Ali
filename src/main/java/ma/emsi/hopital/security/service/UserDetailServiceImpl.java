package ma.emsi.hopital.security.service;

import ma.emsi.hopital.security.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = accountService.loadUserByUsername(username);
        if (appUser == null) throw new UsernameNotFoundException(String.format("User %s not found", username));
        String[] roles = appUser.getRoles().stream().map(u ->u.getRole()).toArray(String[]::new);
        UserDetails userDetails = User
                .withUsername(appUser.getUserName())
                .password(appUser.getPassword())
                .roles(roles)
                .build();

        return userDetails;
    }
}