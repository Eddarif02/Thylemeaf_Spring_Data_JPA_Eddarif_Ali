package ma.emsi.hopital.security.service;

import ma.emsi.hopital.security.entities.AppRole;
import ma.emsi.hopital.security.entities.AppUser;

import javax.sound.midi.VoiceStatus;

public interface AccountService {

    AppUser addNewUser(String username, String password, String email , String confirmPassword);

    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);

    AppUser loadUserByUsername(String username);
}