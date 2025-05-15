package ma.emsi.hopital.security;

import lombok.AllArgsConstructor;
import ma.emsi.hopital.security.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)//pour proteger les route dans le controller
public class SecurityConfig {

    //@Autowired
    private PasswordEncoder passwordEncoder;
    //@Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    //@Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("Saad").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build(),
                User.withUsername("Yassin").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build(),
                User.withUsername("Salma").password("{noop}1234").roles("USER","ADMIN").build()//noop ne pas tiliser un encodeur
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception  {
        //cree un filtre
        httpSecurity.formLogin().loginPage("/login").permitAll();
        httpSecurity.rememberMe().userDetailsService(inMemoryUserDetailsManager()); // ou jdbcUserDetailsManager(dataSource)

        httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**","/h2-console/**").permitAll();
        //httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");//priteger comme ca ou avec @preauthorise
        //httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();//une seule authentification pour toutes les requetes
        httpSecurity.exceptionHandling().accessDeniedPage("/accessDenied");
        httpSecurity.userDetailsService(userDetailServiceImpl);
        return httpSecurity.build();
    }
}
