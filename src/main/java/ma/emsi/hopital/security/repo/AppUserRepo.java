package ma.emsi.hopital.security.repo;

import ma.emsi.hopital.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser, String> {

    AppUser findByUserName(String email);

}
