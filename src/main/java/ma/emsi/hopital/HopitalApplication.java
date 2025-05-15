package ma.emsi.hopital;

import ma.emsi.hopital.entities.Patient;
import ma.emsi.hopital.repository.PatientRepository;

import ma.emsi.hopital.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;




@SpringBootApplication
public class HopitalApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(HopitalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Patient patient = Patient.builder().id(null).nom("Saad").dateNaissance(new Date()).malade(false).score(233).build();

        patientRepository.save(patient);

        Patient patient1= new Patient(null,"Yassin",new Date(),false,123);
        //utilser buuilder ou constructeur ou set
        patientRepository.save(patient1);
        patientRepository.save(new Patient(null,"Saad",new Date(),true,200));
        patientRepository.save(new Patient(null,"Asmaa",new Date(),false,349));
        patientRepository.save(new Patient(null,"Salma",new Date(),false,456));
        patientRepository.save(new Patient(null,"Imane",new Date(),true,236));

    }
    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager) {
        return args -> {

            UserDetails u1= jdbcUserDetailsManager.loadUserByUsername("user11");
            if (u1==null)
                jdbcUserDetailsManager.createUser(User.withUsername("user11").password(passwordEncoder().encode("1234")).roles("USER").build());

            UserDetails u2= jdbcUserDetailsManager.loadUserByUsername("user22");
            if (u2==null)
                jdbcUserDetailsManager.createUser(User.withUsername("user22").password(passwordEncoder().encode("1234")).roles("USER").build());

            UserDetails u3= jdbcUserDetailsManager.loadUserByUsername("admin11");
            if (u3==null)
                jdbcUserDetailsManager.createUser(User.withUsername("admin11").password(passwordEncoder().encode("1234")).roles("USER").build());

            UserDetails u4= jdbcUserDetailsManager.loadUserByUsername("admin22");
            if (u4==null)
                jdbcUserDetailsManager.createUser(User.withUsername("admin22").password(passwordEncoder().encode("1234")).roles("ADMIN").build());


        };
    }

    //@Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService) {
        return args -> {
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");
            accountService.addNewUser("user1", "1234", "user1@gamil.com", "1234");
            accountService.addNewUser("user2", "1234", "user2@gamil.com", "1234");
            accountService.addNewUser("admin", "1234", "user3@gamil.com",  "1234");

            accountService.addRoleToUser("1", "USER");
            accountService.addRoleToUser("2", "USER");
            accountService.addRoleToUser("3", "ADMIN");
        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }
}