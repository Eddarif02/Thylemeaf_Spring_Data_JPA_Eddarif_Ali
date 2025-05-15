package ma.emsi.hopital.security.repo;

import ma.emsi.hopital.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepo extends JpaRepository<AppRole, String> {


}