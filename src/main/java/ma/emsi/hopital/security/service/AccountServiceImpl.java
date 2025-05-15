package ma.emsi.hopital.security.service;

import lombok.AllArgsConstructor;
import ma.emsi.hopital.security.entities.AppRole;
import ma.emsi.hopital.security.entities.AppUser;
import ma.emsi.hopital.security.repo.AppRoleRepo;
import ma.emsi.hopital.security.repo.AppUserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.server.UID;
import java.util.UUID;

@Service//couche metier on utulise les tarnsact
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppUserRepo appUserRepo;
    private AppRoleRepo appRoleRepo;
    private PasswordEncoder passwordEncoder;

    //public AccountServiceImpl(AppUserRepo appUserRepo , AppRoleRepo appRoleRepo) {
    //  this.appUserRepo = appUserRepo;
    //this.appRoleRepo = appRoleRepo;
    //} on utiliser @allargconstructor


    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser = appUserRepo.findByUserName(username);
        if (appUser != null) throw new RuntimeException("User already exists");
        if (!password.equals(confirmPassword)) throw new RuntimeException("Passwords do not match");

        appUser= AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .userName(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();

        AppUser saveAppUser = appUserRepo.save(appUser);

        return saveAppUser;
    }

    @Override
    public AppRole addNewRole(String role) {
        AppRole appRole = appRoleRepo.findById(role).orElse(null);
        if (appRole != null) throw new RuntimeException("Role already exists");
        appRole = AppRole.builder()
                .role(role)
                .build();
        return null;
    }

    @Override
    public void addRoleToUser(String username, String role) {

        AppUser appUser = appUserRepo.findByUserName(username);
        AppRole appRole = appRoleRepo.findById(role).orElse(null);
        appUser.getRoles().add(appRole);
        appUserRepo.save(appUser);
    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppUser appUser = appUserRepo.findByUserName(username);
        AppRole appRole = appRoleRepo.findById(role).orElse(null);
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepo.findByUserName(username);}
}
