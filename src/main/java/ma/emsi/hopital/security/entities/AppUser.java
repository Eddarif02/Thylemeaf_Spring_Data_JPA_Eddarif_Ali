package ma.emsi.hopital.security.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    private String userId;
    private String password;
    @Column(unique=true)
    private String userName;
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)//eager et lazy voir video userdetailservice min 5
    private List<AppRole> roles;
}
